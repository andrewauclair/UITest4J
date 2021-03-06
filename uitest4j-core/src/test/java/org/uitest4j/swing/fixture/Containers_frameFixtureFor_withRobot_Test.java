/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.REMAINDER;
import static javax.swing.BorderFactory.createEmptyBorder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link Containers#frameFixtureFor(org.uitest4j.swing.core.Robot, java.awt.Container)}.
 *
 * @author Christian Rösch
 */
public class Containers_frameFixtureFor_withRobot_Test extends RobotBasedTestCase {
	private MyPanel panel;

	@Override
	@BeforeEach
	public void onSetUp() {
		panel = execute(MyPanel::new);
	}

	@Test
	void should_Place_Container_In_JFrame_Without_Showing() {
		FrameFixture frameFixture = null;
		try {
			frameFixture = Containers.frameFixtureFor(robot, panel);
			frameFixture.requireNotVisible();
			assertThat(frameFixture.target()).isNotNull();
		}
		finally {
			cleanUp(frameFixture);
		}
	}

	private void cleanUp(FrameFixture frameFixture) {
		if (frameFixture != null) {
			frameFixture.cleanUp();
		}
	}

	private static class MyPanel extends JPanel {
		private final GridBagConstraints c = new GridBagConstraints();

		MyPanel() {
			setLayout(new GridBagLayout());
			setBorder(createEmptyBorder(10, 10, 10, 10));
			c.gridx = c.gridy = 0;
			addUsernameInput();
			nextLine();
			addPasswordInput();
		}

		private void addUsernameInput() {
			addInput("Username:", new JTextField(20));
		}

		private void addPasswordInput() {
			addInput("Password:", new JPasswordField(20));
		}

		private void addInput(String label, JComponent input) {
			add(new JLabel(label), c);
			c.gridx++;
			add(Box.createHorizontalStrut(10), c);
			c.gridx++;
			c.gridwidth = REMAINDER;
			add(input, c);
		}

		private void nextLine() {
			c.gridx = 0;
			c.gridwidth = 1;
			c.gridy++;
		}
	}
}
