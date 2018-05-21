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
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.test.recorder.ClickRecorder;

import javax.annotation.Nonnull;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.core.MouseButton.*;

/**
 * @author Andrew Auclair
 */
class BasicFXRobot_clickNodeWithButton_Test extends BasicFXRobot_TestCase {
	private static Collection<Object[]> buttons() {
		return newArrayList(new Object[][]{{LEFT_BUTTON}, {MIDDLE_BUTTON}, {RIGHT_BUTTON}});
	}

	@ParameterizedTest
	@MethodSource("buttons")
	void should_Click_Component_Once_With_Given_Button(@Nonnull MouseButton button) {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(this.button);
		robot.click(this.button, button);
		recorder.clicked(button).timesClicked(1);
	}
}
