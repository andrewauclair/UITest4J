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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

import static org.uitest4j.swing.awt.AWT.centerOf;

/**
 * Tests for {@link ComponentDriver#click(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_clickComponent_Test extends ComponentDriver_TestCase {
  private ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Click_Component() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.click(window.button);
    recorder.wasClicked().clickedAt(centerOf(window.button)).timesClicked(1);
  }

  @Test
  void should_Click_Disabled_Component() {
    showWindow();
    disableButton();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.click(window.button);
    recorder.wasClicked().clickedAt(centerOf(window.button)).timesClicked(1);
  }

  @Test
  void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
    robot.settings().clickOnDisabledComponentsAllowed(false);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    disableButton();
    try {
      ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.click(window.button));
    } finally {
      recorder.wasNotClicked();
    }
  }

  @Test
  void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    try {
      ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.click(window.button));
    } finally {
      recorder.wasNotClicked();
    }
  }
}
