/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.core.MouseButton.*;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component, MouseButton)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_clickComponentWithButton_Test extends BasicRobot_ClickTestCase {
  private static Collection<Object[]> buttons() {
    return newArrayList(new Object[][] { { LEFT_BUTTON }, { MIDDLE_BUTTON }, { RIGHT_BUTTON } });
  }

  @ParameterizedTest
  @MethodSource("buttons")
  void should_Click_Component_Once_With_Given_Button(@Nonnull MouseButton button) {
    JTextField textField = window().textField();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(textField);
    robot().click(textField, button);
    recorder.clicked(button).timesClicked(1);
  }
}
