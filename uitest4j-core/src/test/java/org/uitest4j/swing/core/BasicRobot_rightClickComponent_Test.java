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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.recorder.ClickRecorder;

import javax.swing.*;

import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;

/**
 * Tests for {@link BasicRobot#rightClick(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_rightClickComponent_Test extends BasicRobot_ClickTestCase {
	@Test
	void should_RightClick_Component() {
		JTextField textField = window().textField();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(textField);
		robot().rightClick(textField);
		recorder.clicked(RIGHT_BUTTON).timesClicked(1);
	}
}
