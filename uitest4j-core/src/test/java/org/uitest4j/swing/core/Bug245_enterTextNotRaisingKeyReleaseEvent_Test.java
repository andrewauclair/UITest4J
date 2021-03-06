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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=245">Bug 245</a>.
 *
 * @author Alex Ruiz
 */
class Bug245_enterTextNotRaisingKeyReleaseEvent_Test extends RobotBasedTestCase {
	private JTextField textField;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		textField = window.textField;
		robot.showWindow(window);
	}

	@Test
	void should_Raise_KeyReleased_Event() {
		KeyReleaseListener keyReleaseListener = new KeyReleaseListener();
		textField.addKeyListener(keyReleaseListener);
		robot.focusAndWaitForFocusGain(textField);
		robot.enterText("Hello");
		assertThat(textField.getText()).isEqualTo("Hello");
		assertThat(keyReleaseListener.released()).containsOnly(VK_H, VK_SHIFT, VK_E, VK_L, VK_L, VK_O);
	}

	private static class KeyReleaseListener extends KeyAdapter {
		private final List<Integer> keyCodes = newArrayList();

		@Override
		public void keyReleased(KeyEvent e) {
			keyCodes.add(e.getKeyCode());
		}

		Integer[] released() {
			return keyCodes.toArray(new Integer[0]);
		}
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTextField textField = new JTextField(10);

		private MyWindow() {
			super(Bug245_enterTextNotRaisingKeyReleaseEvent_Test.class);
			addComponents(textField);
		}
	}
}
