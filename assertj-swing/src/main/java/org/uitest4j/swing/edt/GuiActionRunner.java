/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.edt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.uitest4j.swing.exception.UnexpectedException.unexpected;

/**
 * Executes instances of {@link GuiQuery} and {@link GuiTask}.
 *
 * @author Alex Ruiz
 */
@ThreadSafe
public class GuiActionRunner {
  @GuardedBy("this")
  private static boolean executeInEDT = true;

  /**
   * Indicates {@link GuiActionRunner} whether instances of {@link GuiQuery} and {@link GuiTask} should be executed in
   * the event dispatch thread (EDT).
   *
   * @param b if {@code true}, GUI actions are executed in the event dispatch thread (EDT). If {@code false}, GUI
   *          actions are executed in the current thread.
   */
  public static synchronized void executeInEDT(boolean b) {
    executeInEDT = b;
  }

  /**
   * Indicates whether instances of {@link GuiQuery} and {@link GuiTask} should be executed in the event dispatch thread
   * (EDT).
   *
   * @return {@code true} if GUI actions are executed in the event dispatch thread, {@code false} otherwise.
   */
  public static synchronized boolean executeInEDT() {
    return executeInEDT;
  }

  /**
   * Executes the given callable in the event dispatch thread (EDT). This method waits until the query has finished its
   * execution.
   *
   * @param query the query to execute.
   * @param <T> the return type of the action to execute.
   * @return the result of the query executed in the main thread.
   * @throws org.uitest4j.swing.exception.UnexpectedException wrapping any <b>checked</b> exception thrown when executing
   *           the given query in the event dispatch thread (EDT). Unchecked exceptions are re-thrown without any
   *           wrapping.
   * @see #execute(GuiQuery)
   */
  @Nullable public static <T> T execute(@Nonnull Callable<T> query) {
    return execute(new GuiQuery<T>() {
      @Override
      protected T executeInEDT() throws Throwable {
        return query.call();
      }
    });
  }

  /**
   * Executes the given query in the event dispatch thread (EDT). This method waits until the query has finished its
   * execution.
   *
   * @param query the query to execute.
   * @param <T> the return type of the action to execute.
   * @return the result of the query executed in the main thread.
   * @throws org.uitest4j.swing.exception.UnexpectedException wrapping any <b>checked</b> exception thrown when executing
   *           the given query in the
   *           event dispatch thread (EDT). Unchecked exceptions are re-thrown without any wrapping.
   * @see #executeInEDT()
   * @see #execute(Callable)
   */
  @Nullable public static <T> T execute(@Nonnull GuiQuery<T> query) {
    if (!executeInEDT) {
      return executeInCurrentThread(query);
    }
    run(query);
    return resultOf(query);
  }

  @Nullable private static <T> T executeInCurrentThread(@Nonnull GuiQuery<T> query) {
    try {
      return query.executeInEDT();
    } catch (Throwable e) {
      throw unexpected(e);
    }
  }

  /**
   * Executes the given runnable in the event dispatch thread (EDT). This method waits until the task has finished its
   * execution.
   *
   * @param task the task to execute.
   * @throws org.uitest4j.swing.exception.UnexpectedException wrapping any <b>checked</b> exception thrown when executing
   *           the given query in the event dispatch thread (EDT). Unchecked exceptions are re-thrown without any
   *           wrapping.
   * @see #executeInEDT()
   * @see #execute(GuiTask)
   */
  public static void execute(@Nonnull GuiActionRunnable task) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws Throwable {
        task.run();
      }
    });
  }

  /**
   * Executes the given task in the event dispatch thread (EDT). This method waits until the task has finished its
   * execution.
   *
   * @param task the task to execute.
   * @throws org.uitest4j.swing.exception.UnexpectedException wrapping any <b>checked</b> exception thrown when executing
   *           the given query in the
   *           event dispatch thread (EDT). Unchecked exceptions are re-thrown without any wrapping.
   * @see #executeInEDT()
   * @see #execute(GuiActionRunnable)
   */
  public static void execute(@Nonnull GuiTask task) {
    if (!executeInEDT) {
      executeInCurrentThread(task);
      return;
    }
    run(task);
    rethrowCaughtExceptionIn(task);
  }

  private static void executeInCurrentThread(@Nonnull GuiTask task) {
    try {
      task.executeInEDT();
    } catch (Throwable e) {
      throw unexpected(e);
    }
  }

  private static void run(@Nonnull final GuiAction action) {
    if (isEventDispatchThread()) {
      action.run();
      return;
    }
    final CountDownLatch latch = new CountDownLatch(1);
    action.executionNotification(latch);
    invokeLater(action);
    try {
      latch.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Nullable private static <T> T resultOf(@Nonnull GuiQuery<T> query) {
    T result = query.result();
    query.clearResult();
    rethrowCaughtExceptionIn(query);
    return result;
  }

  /**
   * Wraps, with a {@link org.uitest4j.swing.exception.UnexpectedException}, and re-throws any caught exception in the given action.
   *
   * @param action the given action that may have a caught exception during its execution.
   * @throws org.uitest4j.swing.exception.UnexpectedException wrapping any <b>checked</b> exception thrown when executing the given query in the
   *           event dispatch thread (EDT). Unchecked exceptions are re-thrown without any wrapping.
   */
  private static void rethrowCaughtExceptionIn(@Nonnull GuiAction action) {
    Throwable caughtException = action.catchedException();
    action.clearCaughtException();
    if (caughtException == null) {
      return;
    }
    if (caughtException instanceof RuntimeException) {
      appendStackTraceInCurrentThreadToThrowable(caughtException, "execute");
      throw (RuntimeException) caughtException;
    }
    if (caughtException instanceof Error) {
      caughtException.fillInStackTrace();
      throw (Error) caughtException;
    }
    throw unexpected(caughtException);
  }

  /**
   * Borrowed from Throwables in AssertJ Core to break dependency
   */
  public static void appendStackTraceInCurrentThreadToThrowable(Throwable t, String methodToStartFrom) {
    List<StackTraceElement> stackTrace = new ArrayList<>(Arrays.asList(t.getStackTrace()));
    stackTrace.addAll(stackTraceInCurrentThread(methodToStartFrom));
    t.setStackTrace(stackTrace.toArray(new StackTraceElement[0]));
  }

  private static List<StackTraceElement> stackTraceInCurrentThread(String methodToStartFrom) {
	  ArrayList<StackTraceElement> filtered = stackTraceInCurrentThread();
    List<StackTraceElement> toRemove = new ArrayList<>();

    for (Object e : filtered) {
      if (methodToStartFrom.equals(((StackTraceElement) e).getMethodName())) {
        break;
      }

      toRemove.add((StackTraceElement) e);
    }

    filtered.removeAll(toRemove);
    return filtered;
  }

	private static ArrayList<StackTraceElement> stackTraceInCurrentThread() {
		return new ArrayList<>(Arrays.asList(Thread.currentThread().getStackTrace()));
  }
}
