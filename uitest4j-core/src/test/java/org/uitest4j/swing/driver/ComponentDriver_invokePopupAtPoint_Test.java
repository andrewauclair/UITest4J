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
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.uitest4j.swing.test.recorder.ToolkitClickRecorder;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link ComponentDriver#invokePopupMenu(java.awt.Component, java.awt.Point)}.
 *
 * @author Alex Ruiz
 */
class ComponentDriver_invokePopupAtPoint_Test extends ComponentDriver_invokePopup_TestCase {
	private ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Show_JPopupMenu() {
		showWindow();
		Point p = window.textField.getLocation();
		ToolkitClickRecorder recorder = clickRecorder.attachToToolkitFor(window.textField);
		JPopupMenu found = driver.invokePopupMenu(window.textField, p);
		assertThat(found).isSameAs(popupMenu);
		recorder.wasRightClicked().timesClicked(1).clickedAt(p);
	}

	@Test
	void should_Show_JPopupMenu_On_Disabled_Component() {
		disableTextField();
		showWindow();
		Point p = window.textField.getLocation();
		ToolkitClickRecorder recorder = clickRecorder.attachToToolkitFor(window.textField);
		JPopupMenu found = driver.invokePopupMenu(window.textField, p);
		assertThat(found).isSameAs(popupMenu);
		recorder.wasRightClicked().timesClicked(1).clickedAt(p);
	}

	@Test
	void should_Throw_Error_If_Point_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.invokePopupMenu(window.textField, null));
	}

	@Test
	void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
		robot.settings().clickOnDisabledComponentsAllowed(false);
		disableTextField();
		try {
			ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.invokePopupMenu(window.textField, new Point(8, 6)));
		}
		finally {
			assertThatTextFieldIsEmpty();
		}
	}

	@Test
	void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
		try {
			ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.invokePopupMenu(window.textField, new Point(8, 6)));
		}
		finally {
			assertThatTextFieldIsEmpty();
		}
	}
}
