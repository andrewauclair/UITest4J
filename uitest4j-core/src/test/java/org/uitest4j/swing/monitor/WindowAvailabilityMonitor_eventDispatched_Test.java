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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.FailOnThreadViolationRepaintManager;
import org.uitest4j.swing.lock.ScreenLock;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link WindowAvailabilityMonitor}.
 *
 * @author Alex Ruiz
 */
class WindowAvailabilityMonitor_eventDispatched_Test extends WindowAvailabilityMonitor_TestCase {
	private MyWindow window;

	@BeforeAll
	static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Override
	void onSetUp() {
		ScreenLock.instance().acquire(this);
		window = MyWindow.createNew(getClass());
	}

	@AfterEach
	void tearDown() {
		try {
			window.destroy();
		}
		finally {
			ScreenLock.instance().release(this);
		}
	}

	@Test
	void should_Mark_Source_Window_As_Ready_If_Event_Is_MouseEvent() {
		monitor.eventDispatched(mouseEvent(window));
		verify(windows).markAsReady(window);
	}

	@Test
	void should_Mark_Source_Window_Ancestor_As_Ready_If_Event_Is_MouseEvent() {
		JTextField source = window.textField;
		monitor.eventDispatched(mouseEvent(source));
		verify(windows).markAsReady(window);
	}

	@Test
	void should_Not_Mark_Source_Window_As_Ready_If_Event_Is_Not_MouseEvent() {
		monitor.eventDispatched(new KeyEvent(window, 8, 9238, 0, 0, 'a'));
		verifyZeroInteractions(windows);
	}

	private MouseEvent mouseEvent(Component source) {
		return new MouseEvent(source, 8, 8912, 0, 0, 0, 0, false, 0);
	}

	static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		final JTextField textField = new JTextField("Hello");

		private MyWindow(Class<?> testClass) {
			super(testClass);
			addComponents(textField);
		}
	}
}
