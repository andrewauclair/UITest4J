/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.input;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.awt.ToolkitStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.awt.Toolkits.newToolkitStub;

/**
 * Tests for {@link DragAwareEventNormalizer#startListening(java.awt.Toolkit, java.awt.event.AWTEventListener, long)}.
 *
 * @author Alex Ruiz
 */
class DragAwareEventNormalizer_startListening_Test extends DragAwareEventNormalizer_TestCase {
	@Test
	void should_Replace_EventQueue_When_Starts_Listening() {
		ToolkitStub toolkit = newToolkitStub();
		EventQueueStub eventQueue = new EventQueueStub();
		toolkit.eventQueue(eventQueue);
		int mask = 8;
		eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
		checkEventNormalizerInToolkit(toolkit, eventNormalizer, mask);
		assertThat(eventQueue.pushedEventQueue).isInstanceOf(DragAwareEventQueue.class);
	}
}
