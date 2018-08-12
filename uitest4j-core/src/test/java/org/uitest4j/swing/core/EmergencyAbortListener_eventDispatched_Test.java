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
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.test.awt.TestAWTEvents.newAWTEventMock;
import static org.uitest4j.swing.test.awt.Toolkits.singletonToolkitMock;
import static org.uitest4j.swing.test.builder.JButtons.button;

/**
 * Tests for {@link EmergencyAbortListener#eventDispatched(AWTEvent)}.
 *
 * @author Alex Ruiz
 */
class EmergencyAbortListener_eventDispatched_Test extends EDTSafeTestCase {
	private TestTerminator terminator;
	private EmergencyAbortListener listener;

	@BeforeEach
	void setUp() {
		terminator = mock(TestTerminator.class);
		listener = new EmergencyAbortListener(singletonToolkitMock(), terminator);
	}

	@Test
	void should_Not_Terminate_Tests_If_Event_Id_Is_Not_KeyPressed() {
		AWTEvent event = newAWTEventMock();
		int eventId = KEY_PRESSED + 1;
		when(event.getID()).thenReturn(eventId);
		listener.eventDispatched(event);
		verifyZeroInteractions(terminator);
	}

	@Test
	void should_Not_Terminate_Tests_If_Event_Is_Not_KeyEvent() {
		AWTEvent event = newAWTEventMock();
		when(event.getID()).thenReturn(KEY_PRESSED);
		listener.eventDispatched(event);
		verifyZeroInteractions(terminator);
	}

	@Test
	void should_Not_Terminate_Tests_If_Key_Code_Does_Not_Match() {
		KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, 0, VK_Z, 'Z');
		listener.eventDispatched(event);
		verifyZeroInteractions(terminator);
	}

	@Test
	void should_Not_Terminate_Tests_If_Modifiers_Do_Not_Match() {
		KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, 0, VK_A, 'A');
		listener.eventDispatched(event);
		verifyZeroInteractions(terminator);
	}

	@Test
	void should_Terminate_Tests_If_Key_Combination_Matches() {
		KeyEvent event = new KeyEvent(button().createNew(), KEY_PRESSED, 0, CTRL_DOWN_MASK | SHIFT_DOWN_MASK, VK_A, 'A');
		listener.eventDispatched(event);
		verify(terminator).terminateTests();
	}
}
