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

import org.junit.jupiter.api.Test;
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JOptionPanes.optionPane;
import static org.uitest4j.swing.test.swing.JOptionPaneLauncher.launch;

/**
 * Tests for {@link JOptionPaneFixture#JOptionPaneFixture(SwingRobot)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JOptionPaneFixture_constructor_withSwingRobot_Test extends SwingRobotBasedTestCase {
	private JOptionPane target;

	@Override
	protected void onSetUp() {
		target = optionPane().withMessage("Hello").createNew();
		launch(target);
	}

	@Test
	public void should_Lookup_Showing_JOptionPane() {
		JOptionPaneFixture fixture = new JOptionPaneFixture(robot);
		assertThat(fixture.robot()).isSameAs(robot);
		assertThat(fixture.target()).isSameAs(target);
	}
}
