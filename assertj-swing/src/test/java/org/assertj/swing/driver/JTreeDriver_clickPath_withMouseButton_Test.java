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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.uitest4j.swing.core.MouseButton;
import org.assertj.swing.exception.LocationUnavailableException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTreeDriver#clickPath(javax.swing.JTree, String, org.uitest4j.swing.core.MouseButton)}.
 * 
 * @author Alex Ruiz
 */
class JTreeDriver_clickPath_withMouseButton_Test extends JTreeDriver_clickCell_TestCase {
  private ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Throw_Error_If_MouseButton_Is_Null() {
    MouseButton button = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.clickPath(tree, "root", button));
  }

  @Test
  void should_Click_Path() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tree);
    driver.clickPath(tree, "root/branch1/branch1.1/branch1.1.1", RIGHT_BUTTON);
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    String clickedPath = pathAtPoint(tree, recorder.pointClicked(), driver.separator());
    assertThat(clickedPath).isEqualTo("root/branch1/branch1.1/branch1.1.1");
  }

  @Test
  void should_Throw_Error_If_Path_Not_Found() {
    showWindow();
    ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> driver.clickPath(tree, "another", RIGHT_BUTTON), "Unable to find path 'another'");
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Disabled() {
    disableTree();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.clickPath(tree, "root/branch1", RIGHT_BUTTON));
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.clickPath(tree, "root/branch1", RIGHT_BUTTON));
  }
}
