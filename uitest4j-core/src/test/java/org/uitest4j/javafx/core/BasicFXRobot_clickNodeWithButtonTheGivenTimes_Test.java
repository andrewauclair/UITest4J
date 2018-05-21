/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.javafx.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.javafx.JavaFX;
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.test.recorder.ClickRecorder;

import javax.annotation.Nonnull;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.core.ClickingDataProvider.clickingData;

/**
 * @author Andrew Auclair
 */
class BasicFXRobot_clickNodeWithButtonTheGivenTimes_Test extends BasicFXRobot_TestCase {
	@Nonnull
	private static Collection<Object[]> buttons() {
		return newArrayList(clickingData());
	}

	@ParameterizedTest
	@MethodSource("buttons")
	void should_Click_Component_With_Given_Mouse_Button_And_Given_Number_Of_Times(@Nonnull MouseButton button, int times) {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(this.button);
		robot.click(this.button, button, times);
		recorder.clicked(button).timesClicked(times).clickedAt(JavaFX.centerOf(this.button));
	}
}
