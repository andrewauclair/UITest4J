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

import org.assertj.swing.test.recorder.ClickRecorder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.awt.AWT.locationOnScreenOf;
import static org.uitest4j.swing.awt.AWT.visibleCenterOf;
import static org.uitest4j.swing.core.ClickingDataProvider.clickingData;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component, java.awt.Point, MouseButton, int)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_clickComponentAtPointWithButtonTheGivenTimes_Test extends BasicRobot_ClickTestCase {
  private static Collection<Object[]> buttons() {
    return newArrayList(clickingData());
  }

  @ParameterizedTest
  @MethodSource("buttons")
  void should_Click_At_Given_Point_With_Given_Mouse_Button_And_Given_Number_Of_Times(MouseButton button, int times) throws InterruptedException {
    // TODO Without this sleep these tests fail on Ubuntu because the dialog resizes after we get the sizes. Maybe it's a VM thing. Going to try it on real hardware.
    Thread.sleep(750);
    JTextField textField = window().textField();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(textField);
    Point screenLocation = checkNotNull(locationOnScreenOf(textField));
    Point visibleCenter = visibleCenterOf(textField);
    screenLocation.translate(visibleCenter.x, visibleCenter.y);
    robot().click(screenLocation, button, times);
    recorder.clicked(button).timesClicked(times).clickedAt(visibleCenter);
  }
}
