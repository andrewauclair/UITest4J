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

import java.awt.*;

import static org.uitest4j.swing.awt.AWT.centerOf;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component, java.awt.Point)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_clickComponentAtPoint_Test extends ComponentDriver_TestCase {
	private ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Click_Component_At_Given_Point() {
		showWindow();
		Point center = centerOf(window.button);
		Point where = new Point(center.x + 1, center.y + 1);
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		driver.click(window.button, where);
		recorder.wasClicked().clickedAt(where).timesClicked(1);

	}

	@Test
	void should_Click_Disabled_Component() {
		showWindow();
		disableButton();
		Point center = centerOf(window.button);
		Point where = new Point(center.x + 1, center.y + 1);
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		driver.click(window.button, where);
		recorder.wasClicked().clickedAt(where).timesClicked(1);
	}

	@Test
	void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
		robot.settings().clickOnDisabledComponentsAllowed(false);
		disableButton();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);

		try {
			ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.click(window.button, new Point(10, 10)));
		}
		finally {
			recorder.wasNotClicked();
		}
	}

	@Test
	void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		try {
			ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.click(window.button, new Point(10, 10)));
		}
		finally {
			recorder.wasNotClicked();
		}
	}
}
