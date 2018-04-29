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
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.JInternalFrameAction.DEICONIFY;
import static org.uitest4j.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.uitest4j.swing.driver.JInternalFrameSetIconTask.setIcon;

/**
 * Tests for {@link JInternalFrameDriver#deiconify(JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JInternalFrameDriver_deiconify_Test extends JInternalFrameDriver_TestCase {
  @Test
  void should_Not_Deiconify_Already_Iconified_JInternalFrame() {
    showWindow();
    deiconify();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @RunsInEDT
  private void deiconify() {
    setIcon(internalFrame, DEICONIFY);
    robot.waitForIdle();
  }

  @Test
  void should_Throw_Error_If_JInternalFrame_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.deiconify(internalFrame));
  }
}
