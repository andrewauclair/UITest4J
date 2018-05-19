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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.test.recorder.ClickRecorder;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.awt.AWT.centerOf;
import static org.uitest4j.swing.core.ClickingDataProvider.clickingData;

/**
 * Tests for {@link BasicSwingRobot#click(java.awt.Point, MouseButton, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicSwingRobot_clickComponentWithButtonTheGivenTimes_Test extends BasicRobot_ClickTestCase {
	@Nonnull
	private static Collection<Object[]> buttons() {
		return newArrayList(clickingData());
	}

	@ParameterizedTest
	@MethodSource("buttons")
	void should_Click_Component_With_Given_Mouse_Button_And_Given_Number_Of_Times(@Nonnull MouseButton button, int times) {
		JTextField textField = window().textField();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(textField);
		robot().click(textField, button, times);
		recorder.clicked(button).timesClicked(times).clickedAt(centerOf(textField));
	}
}
