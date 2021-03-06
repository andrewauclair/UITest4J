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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.input.InputState;
import org.uitest4j.swing.monitor.WindowMonitor;
import org.uitest4j.swing.test.awt.TestComponents;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.util.StopWatch;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.test.awt.TestAWTEvents.singletonAWTEventMock;
import static org.uitest4j.swing.test.awt.Toolkits.newToolkitMock;
import static org.uitest4j.swing.test.util.StopWatch.startNewStopWatch;

/**
 * Tests for {@link AWTEventPoster#postEvent(Component, AWTEvent)}.
 *
 * @author Alex Ruiz
 */
class AWTEventPoster_postEvent_Test extends EDTSafeTestCase {
	private static final int WAIT_DELAY = 1000;
	private Toolkit toolkit;
	private InputState inputState;
	private WindowMonitor monitor;
	private Settings settings;
	private EventQueue eventQueue;
	private AWTEvent event;
	private AWTEventPoster poster;

	@BeforeEach
	void setUp() {
		toolkit = newToolkitMock();
		inputState = mock(InputState.class);
		monitor = mock(WindowMonitor.class);
		settings = mock(Settings.class);
		eventQueue = mock(EventQueue.class);
		event = singletonAWTEventMock();
		poster = new AWTEventPoster(toolkit, inputState, monitor, settings);
	}

	@Test
	void should_Post_Event_In_Component_EventQueue_If_Component_Is_Not_Null() {
		final Component c = TestComponents.newComponentMock();
		when(settings.delayBetweenEvents()).thenReturn(WAIT_DELAY);
		when(monitor.eventQueueFor(c)).thenReturn(eventQueue);
		postEventAndAssertItWaited(c);
		verify(eventQueue).postEvent(event);
		verify(inputState).update(event);
	}

	@Test
	void should_Post_Event_In_System_EventQueue_Ff_Component_Is_Null() {
		when(settings.delayBetweenEvents()).thenReturn(WAIT_DELAY);
		when(toolkit.getSystemEventQueue()).thenReturn(eventQueue);
		postEventAndAssertItWaited(null);
		verify(inputState).update(event);
		verify(eventQueue).postEvent(event);
	}

	private void postEventAndAssertItWaited(Component c) {
		StopWatch stopWatch = startNewStopWatch();
		poster.postEvent(c, event);
		stopWatch.stop();
		assertThat(stopWatch.ellapsedTime()).isGreaterThanOrEqualTo(WAIT_DELAY);
	}
}
