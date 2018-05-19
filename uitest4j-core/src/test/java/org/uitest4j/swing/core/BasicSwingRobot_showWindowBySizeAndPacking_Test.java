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
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;

/**
 * Tests for {@link BasicSwingRobot#showWindow(java.awt.Window, java.awt.Dimension, boolean)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicSwingRobot_showWindowBySizeAndPacking_Test extends EDTSafeTestCase {
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
	void should_Not_Pack_Window_As_Specified() {
		// TODO split this test case in 2+
		Dimension size = new Dimension(100, 100);
		WindowToShow result = execute(WindowToShow::new);
		WindowToShow w = checkNotNull(result);
		robot.showWindow(w, size, false);
		assertThat(sizeOf(w)).isEqualTo(size);
		assertThat(w.wasPacked()).isFalse();
	}

	private static class WindowToShow extends JWindow {
		private boolean packed;

		@Override
		public void pack() {
			packed = true;
			super.pack();
		}

		boolean wasPacked() {
			return packed;
		}
	}
}
