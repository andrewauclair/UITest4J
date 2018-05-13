/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.monitor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.awt.event.WindowEvent.*;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.monitor.TestContexts.newMockContext;
import static org.uitest4j.swing.monitor.TestWindows.newWindowsMock;

/**
 * Tests for {@link ContextMonitor#eventDispatched(java.awt.AWTEvent)}.
 *
 * @author Alex Ruiz
 */
class ContextMonitor_eventDispatched_ParameterizedTest extends EDTSafeTestCase {
	private ContextMonitor monitor;

	private Windows windows;
	private Context context;
	private TestWindow window;

	private static Collection<Object[]> eventsBetweenWindowFirstAndWindowLast() {
		List<Object[]> ids = newArrayList();
		for (int id = WINDOW_FIRST; id <= WINDOW_LAST; id++) {
			if (id == WINDOW_OPENED || id == WINDOW_CLOSED || id == WINDOW_CLOSING) {
				continue;
			}
			ids.add(new Object[]{id});
		}
		return ids;
	}

	@BeforeEach
	void setUp() {
		window = TestWindow.createNewWindow(getClass());
		windows = newWindowsMock();
		context = newMockContext();
		monitor = new ContextMonitor(context, windows);
	}

	@AfterEach
	void tearDown() {
		window.destroy();
	}

	@ParameterizedTest
	@MethodSource("eventsBetweenWindowFirstAndWindowLast")
	void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowNotInContext(int eventId) {
		when(context.rootWindows()).thenReturn(new ArrayList<>());
		when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
		dispatchEventToMonitor(eventId);
		verify(context).addContextFor(window);
		verify(windows).attachNewWindowVisibilityMonitor(window);
		verify(windows).markAsShowing(window);
	}

	@ParameterizedTest
	@MethodSource("eventsBetweenWindowFirstAndWindowLast")
	void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowInContextAndClosed(int eventId) {
		when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
		when(context.rootWindows()).thenReturn(frameInList());
		when(windows.isClosed(window)).thenReturn(true);
		dispatchEventToMonitor(eventId);
		verify(context).addContextFor(window);
		verify(windows).attachNewWindowVisibilityMonitor(window);
		verify(windows).markAsShowing(window);
	}

	@ParameterizedTest
	@MethodSource("eventsBetweenWindowFirstAndWindowLast")
	void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowInContextAndNotClosed(int eventId) {
		when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
		when(context.rootWindows()).thenReturn(frameInList());
		when(windows.isClosed(window)).thenReturn(false);
		dispatchEventToMonitor(eventId);
		// TODO(Alex): verify something!
	}

	private void dispatchEventToMonitor(int eventId) {
		monitor.eventDispatched(new ComponentEvent(window, eventId));
	}

	private List<Window> frameInList() {
		List<Window> rootWindows = new ArrayList<>();
		rootWindows.add(window);
		return rootWindows;
	}
}
