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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.recorder.ClickRecorder;

import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;

/**
 * @author Andrew Auclair
 */
class BasicFXRobot_clickNode_Test extends BasicFXRobot_TestCase {
	@Test
	void clicks_node() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(button);
		robot.click(button);
		recorder.clicked(LEFT_BUTTON).timesClicked(1);
	}
}
