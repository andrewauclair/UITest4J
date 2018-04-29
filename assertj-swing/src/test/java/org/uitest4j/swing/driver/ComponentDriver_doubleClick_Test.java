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

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

import static org.uitest4j.swing.awt.AWT.centerOf;

/**
 * Tests for {@link ComponentDriver#doubleClick(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_doubleClick_Test extends ComponentDriver_TestCase {
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Double_Click_Component() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.doubleClick(window.button);
    recorder.wasDoubleClicked().clickedAt(centerOf(window.button));
  }

  @Test
  void should_Double_Click_Disabled_Component() {
    showWindow();
    disableButton();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    driver.doubleClick(window.button);
    recorder.wasDoubleClicked().clickedAt(centerOf(window.button));
  }

  @Test
  void should_Throw_Error_If_Component_Is_Disabled_And_ClickOnDisabledAllowd_Is_False() {
    robot.settings().clickOnDisabledComponentsAllowed(false);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    disableButton();
    try {
      ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.doubleClick(window.button));
    } finally {
      recorder.wasNotClicked();
    }
  }

  @Test
  void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    try {
      ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.doubleClick(window.button));
    } finally {
      recorder.wasNotClicked();
    }
  }
}
