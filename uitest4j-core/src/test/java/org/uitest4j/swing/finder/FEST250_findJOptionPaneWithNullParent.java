/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.finder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.BasicRobot;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.fixture.JOptionPaneFixture;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.JOptionPaneLauncher;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.finder.JOptionPaneFinder.findOptionPane;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-250" target="_blank">FEST-250</a>
 *
 * @author Alex Ruiz
 */
class FEST250_findJOptionPaneWithNullParent extends EDTSafeTestCase {
	private Robot robot;

	@BeforeEach
	void setUp() {
		JOptionPane optionPane = execute(() -> new JOptionPane("Hello World!"));
		JOptionPaneLauncher.launch(optionPane);
		robot = BasicRobot.robotWithCurrentAwtHierarchy();
	}

	@AfterEach
	void tearDown() {
		robot.cleanUp();
	}

	@Test
	void should_Find_JOptionPane() {
		JOptionPaneFixture optionPane = findOptionPane().using(robot);
		optionPane.requireMessage("Hello World!");
	}
}
