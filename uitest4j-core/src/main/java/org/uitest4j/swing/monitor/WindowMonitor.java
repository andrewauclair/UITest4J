/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.monitor;

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.util.ToolkitProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collection;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Monitor that keeps track of all known root AWT or Swing {@code Window}s (showing, hidden, closed).
 *
 * @author Alex Ruiz
 */
public class WindowMonitor {
	private final Context context;
	private final Windows windows;
	private final WindowStatus windowStatus;

	/**
	 * <p>
	 * Create an instance of WindowTracker which will track all windows coming and going on the current and subsequent
	 * {@code AppContext}s.
	 * </p>
	 *
	 * <p>
	 * <strong>WARNING:</strong> if an applet loads this class, it will only ever see stuff in its own {@code AppContext}.
	 * </p>
	 *
	 * @param toolkit the {@code Toolkit} to use.
	 */
	@RunsInCurrentThread
	WindowMonitor(@Nonnull Toolkit toolkit) {
		this(toolkit, new Context(toolkit), new WindowStatus(new Windows()));
	}

	public ContextMonitor contextMonitor;

	// Used for tests
	@RunsInCurrentThread
	WindowMonitor(@Nonnull Toolkit toolkit, @Nonnull Context context, @Nonnull WindowStatus windowStatus) {
		this.context = context;
		this.windowStatus = windowStatus;
		windows = windowStatus.windows();
		contextMonitor = new ContextMonitor(context, windows);
		contextMonitor.attachTo(toolkit);
		WindowAvailabilityMonitor windowAvailabilityMonitor = new WindowAvailabilityMonitor(windows);
		windowAvailabilityMonitor.attachTo(toolkit);
		populateExistingWindows();
	}

	private void populateExistingWindows() {
		for (Frame f : Frame.getFrames()) {
			examine(Objects.requireNonNull(f));
		}
	}

	@RunsInCurrentThread
	private void examine(@Nonnull Window w) {
		windows.attachNewWindowVisibilityMonitor(w);
		for (Window owned : w.getOwnedWindows()) {
			examine(Objects.requireNonNull(owned));
		}
		windows.markExisting(w);
		context.addContextFor(w);
	}

	/**
	 * Returns whether the AWT or Swing {@code Window} is ready to receive OS-level event input. A {@code Window}'s
	 * "isShowing" flag may be set {@code true} before the {@code WINDOW_OPENED} event is generated, and even after the
	 * {@code WINDOW_OPENED} event is sent, the {@code Window} peer is not guaranteed to be ready.
	 *
	 * @param w the given {@code Window}.
	 * @return whether the {@code Window} is ready to receive OS-level event input.
	 */
	public boolean isWindowReady(@Nonnull Window w) {
		if (windows.isReady(w)) {
			return true;
		}
		windowStatus.checkIfReady(w);
		return false;
	}

	/**
	 * Returns the event queue corresponding to the given AWT or Swing {@code Compoenent}. In most cases, this is the same
	 * as {@code Component.getToolkit().getSystemEventQueue()}, but in the case of applets will bypass the
	 * {@code AppContext} and provide the real event queue.
	 *
	 * @param c the given {@code Component}.
	 * @return the event queue corresponding to the given component.
	 */
	@Nullable
	public EventQueue eventQueueFor(@Nonnull Component c) {
		return context.eventQueueFor(c);
	}

	/**
	 * @return all known event queues.
	 */
	@Nonnull
	public Collection<EventQueue> allEventQueues() {
		return context.allEventQueues();
	}

	/**
	 * Return all available root {@code Window}s. A root {@code Window} is one that has a {@code null} parent. Nominally
	 * this means a list similar to that returned by {@code Frame.getFrames()}, but in the case of an {@code Applet} may
	 * return a few dialogs as well.
	 *
	 * @return all available root {@code Window}s.
	 */
	@Nonnull
	public Collection<Window> rootWindows() {
		return context.rootWindows();
	}

	/**
	 * @return the singleton instance of this class.
	 */
	@RunsInEDT
	@Nonnull
	public static WindowMonitor instance() {
		return SingletonLazyLoader.INSTANCE;
	}

	@RunsInEDT
	private static class SingletonLazyLoader {
		static final WindowMonitor INSTANCE = execute(new GuiQuery<>() {
			@Override
			protected WindowMonitor executeInEDT() {
				return new WindowMonitor(ToolkitProvider.instance().defaultToolkit());
			}
		});
	}
}
