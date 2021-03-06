/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.timing.Condition;

import javax.annotation.Nonnull;
import java.awt.*;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.awt.TestAWTEvents.singletonAWTEventMock;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Tests for {@link EventDispatchThreadedEventListener#eventDispatched(AWTEvent)}.
 *
 * @author Alex Ruiz
 */
class EventDispatchThreadedEventListener_eventDispatched_Test {
	private AWTEvent event;
	private Listener listener;

	@BeforeEach
	void setUp() {
		event = singletonAWTEventMock();
		listener = new Listener();
	}

	@Test
	void should_Always_Process_Event_In_EDT() {
		listener.eventDispatched(event);
		pause(new Condition("event to be processed in EDT") {
			@Override
			public boolean test() {
				return listener.event == event && listener.wasProcessedInEventDispatchThread;
			}
		});
	}

	@Test
	void should_Process_Event_Directly_If_Called_In_EDT() throws Exception {
		execute(() -> listener.eventDispatched(event));
		assertThat(listener.event).isSameAs(event);
		assertThat(listener.wasProcessedInEventDispatchThread).isTrue();
	}

	private static class Listener extends EventDispatchThreadedEventListener {
		AWTEvent event;
		boolean wasProcessedInEventDispatchThread;

		Listener() {
		}

		@Override
		protected void processEvent(@Nonnull AWTEvent newEvent) {
			this.event = newEvent;
			wasProcessedInEventDispatchThread = isEventDispatchThread();
		}
	}
}
