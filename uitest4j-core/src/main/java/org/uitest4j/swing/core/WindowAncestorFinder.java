/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.uitest4j.swing.hierarchy.ComponentHierarchy;
import org.uitest4j.swing.hierarchy.ExistingHierarchy;
import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.awt.AWT.invokerOf;

/**
 * Looks up an AWT or Swing {@code Component}'s {@code Window} ancestor.
 * 
 * @author Yvonne Wang
 */
public final class WindowAncestorFinder {
  private static ComponentHierarchy hierarchy = new ExistingHierarchy();

  /**
   * <p>
   * Similar to {@code javax.swing.SwingUtilities#getWindowAncestor(Component)}, but returns the AWT or Swing
   * {@code Component} itself if it is a {@code Window}, or the invoker's {@code Window} if on a pop-up.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT). Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the {@code Component} to get the {@code Window} ancestor of.
   * @return the {@code Window} ancestor of the given {@code Component}, the {@code Component} itself if it is a
   *         {@code Window}, or the invoker's {@code Window} if on a pop-up.
   */
  @RunsInCurrentThread
  @Nullable public static Window windowAncestorOf(@Nonnull Component c) {
    return findWindowAncestor(c);
  }

  @RunsInCurrentThread
  @Nullable private static Window findWindowAncestor(@Nullable Component c) {
    if (c == null) {
      return null;
    }
    if (c instanceof Window) {
      return (Window) c;
    }
    if (c instanceof MenuElement) {
      Component invoker = invokerOf(c);
      if (invoker != null) {
        return windowAncestorOf(invoker);
      }
    }
    return findWindowAncestor(hierarchy.parentOf(c));
  }

  private WindowAncestorFinder() {
  }
}
