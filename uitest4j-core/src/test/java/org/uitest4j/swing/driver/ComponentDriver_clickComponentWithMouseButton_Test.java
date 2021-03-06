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
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.awt.AWT.centerOf;
import static org.uitest4j.swing.core.MouseButton.*;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component, org.uitest4j.swing.core.MouseButton)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_clickComponentWithMouseButton_Test extends ComponentDriver_TestCase {
	private ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Click_Component_With_Left_Mouse_Button_Once() {
		shouldClickComponentWith(LEFT_BUTTON);
	}

	@Test
	void should_Click_Component_With_Middle_Mouse_Button_Once() {
		shouldClickComponentWith(MIDDLE_BUTTON);
	}

	@Test
	void should_Click_Component_With_Right_Mouse_Button_Once() {
		shouldClickComponentWith(RIGHT_BUTTON);
	}

	private void shouldClickComponentWith(MouseButton mouseButton) {
		showWindow();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		driver.click(window.button, mouseButton);
		recorder.wasClickedWith(mouseButton).clickedAt(centerOf(window.button)).timesClicked(1);
	}

	@Test
	void should_Throw_Error_If_MouseButton_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.click(window.button, (MouseButton) null));
	}

	@Test
	void should_Click_Disabled_Component() {
		disableButton();
		shouldClickComponentWith(LEFT_BUTTON);
	}

	@Test
	void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
		robot.settings().clickOnDisabledComponentsAllowed(false);
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		disableButton();
		try {
			ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.click(window.button, RIGHT_BUTTON));
		}
		finally {
			recorder.wasNotClicked();
		}
	}

	@Test
	void should_Throw_Error_When_Clicking_Component_Not_Showing() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		try {
			ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.click(window.button, RIGHT_BUTTON));
		}
		finally {
			recorder.wasNotClicked();
		}
	}
}
