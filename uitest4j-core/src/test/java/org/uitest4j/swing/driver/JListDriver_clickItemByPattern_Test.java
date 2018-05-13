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
import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import java.awt.*;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;

/**
 * Tests for
 * {@link JListDriver#clickItem(javax.swing.JList, java.util.regex.Pattern, org.uitest4j.swing.core.MouseButton, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_clickItemByPattern_Test extends JListDriver_TestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Click_Item_With_Text_Matching_Given_Pattern() {
		clearSelection();
		showWindow();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(list);
		driver.clickItem(list, Pattern.compile("tw.*"), RIGHT_BUTTON, 2);
		recorder.clicked(RIGHT_BUTTON).timesClicked(2);
		Point pointClicked = recorder.pointClicked();
		assertThat(locationToIndex(pointClicked)).isEqualTo(1);
	}

	@Test
	void should_Throw_Error_If_JList_Is_Disabled() {
		disableList();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.clickItem(list, Pattern.compile("two"), RIGHT_BUTTON, 2));
	}

	@Test
	void should_Throw_Error_If_JList_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.clickItem(list, Pattern.compile("two"), RIGHT_BUTTON, 2));
	}

	@Test
	void should_Throw_Error_If_Item_To_Click_Was_Not_Found() {
		showWindow();
		assertThrows(LocationUnavailableException.class, () -> driver.clickItem(list, Pattern.compile("hello"), RIGHT_BUTTON, 2));
	}
}
