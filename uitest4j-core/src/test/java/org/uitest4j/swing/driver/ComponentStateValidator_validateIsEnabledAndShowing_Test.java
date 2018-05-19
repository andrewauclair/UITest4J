/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Tests for {@link ComponentPreconditions#checkEnabledAndShowing(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class ComponentStateValidator_validateIsEnabledAndShowing_Test extends SwingRobotBasedTestCase {
	private TestWindow window;

	@Override
	protected void onSetUp() {
		window = TestWindow.createNewWindow(getClass());
	}

	@Test
	void should_Not_Throw_Error_If_Component_Is_Enabled_And_Showing_On_The_Screen() {
		robot.showWindow(window);
		validateWindowIsEnabledAndShowing(window);
	}

	@Test
	void should_Throw_Error_If_Component_Is_Disabled() {
		robot.showWindow(window);
		disable(window);
		robot.waitForIdle();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> validateWindowIsEnabledAndShowing(window));
	}

	@Test
	void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> validateWindowIsEnabledAndShowing(window));
	}

	@RunsInEDT
	private static void validateWindowIsEnabledAndShowing(final Component c) {
		execute(() -> ComponentPreconditions.checkEnabledAndShowing(c));
	}
}
