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
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;

/**
 * Tests for {@link AbstractButtonDriver#click(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class AbstractButtonDriver_click_Test extends AbstractButtonDriver_TestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Click_Button() {
		showWindow();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(checkBox);
		driver.click(checkBox);
		recorder.wasClickedWith(LEFT_BUTTON);
	}

	@Test
	void should_Click_Disabled_Button() {
		showWindow();
		disableCheckBox();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(checkBox);
		driver.click(checkBox);
		recorder.wasClickedWith(LEFT_BUTTON);
	}

	@Test
	void should_Throw_Error_If_AbstractButton_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
		robot.settings().clickOnDisabledComponentsAllowed(false);
		disableCheckBox();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(checkBox);
		try {
			ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.click(checkBox));
		}
		finally {
			recorder.wasNotClicked();
		}
	}

	@Test
	void should_Throw_Error_If_AbstractButton_Is_Not_Showing_On_The_Screen() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(checkBox);
		try {
			ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.click(checkBox));
		}
		finally {
			recorder.wasNotClicked();
		}
	}
}
