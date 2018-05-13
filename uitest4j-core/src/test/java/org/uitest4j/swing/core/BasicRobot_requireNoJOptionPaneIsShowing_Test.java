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
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.timing.Condition;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Tests for {@link BasicRobot#requireNoJOptionPaneIsShowing()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_requireNoJOptionPaneIsShowing_Test extends BasicRobot_TestCase {
	private JButton button;

	@RunsInEDT
	@Override
	void beforeShowingWindow() {
		execute(() -> {
			button = new JButton("Click Me");
			button.addActionListener(e -> JOptionPane.showMessageDialog(window(), "A Message"));
			window().add(button);
		});
	}

	@Test
	void should_Pass_If_No_JOptionPane_Is_Showing() {
		robot().requireNoJOptionPaneIsShowing();
	}

	@Test
	void should_Fail_If_A_JOptionPane_Is_Showing() {
		robot().click(button);
		pauseTillJOptionPaneIsShowing();
		ExpectedException.assertAssertionError(() -> robot().requireNoJOptionPaneIsShowing(), "Expecting no JOptionPane to be showing");
	}

	private void pauseTillJOptionPaneIsShowing() {
		pause(new Condition("JOptionPane is showing") {
			@Override
			public boolean test() {
				Collection<Component> found = robot().finder().findAll(new TypeMatcher(JOptionPane.class));
				return !found.isEmpty();
			}
		});
	}
}
