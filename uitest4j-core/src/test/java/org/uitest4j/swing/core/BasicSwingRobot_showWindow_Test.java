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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;
import java.util.logging.Logger;

import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link BasicSwingRobot#showWindow(java.awt.Window)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicSwingRobot_showWindow_Test extends EDTSafeTestCase {
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private BasicSwingRobot robot;

	@BeforeEach
	void setUp() {
		robot = (BasicSwingRobot) BasicSwingRobot.robotWithNewAwtHierarchy();
	}

	@AfterEach
	void tearDown() {
		robot.cleanUp();
	}

	@Test
	void should_Throw_Error_If_Window_Never_Shown() {
		AlwaysInvisibleFrame window = AlwaysInvisibleFrame.createNew();
		LOGGER.info(concat("Waiting for ", AlwaysInvisibleFrame.class.getSimpleName(), " to show up"));
		ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> robot.showWindow(window), "Timed out waiting for Window to open");
	}

	private static class AlwaysInvisibleFrame extends JFrame {
		@RunsInEDT
		static AlwaysInvisibleFrame createNew() {
			return execute(AlwaysInvisibleFrame::new);
		}

		@Override
		public void setVisible(boolean b) {
			super.setVisible(false);
		}
	}
}
