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

import org.uitest4j.swing.core.MouseClickInfo;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;
import static org.uitest4j.swing.core.MouseClickInfo.rightButton;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTreeDriver#clickRow(javax.swing.JTree, int, MouseClickInfo)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_clickRow_withMouseClickInfo_Test extends JTreeDriver_clickCell_TestCase {
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private static MouseClickInfo mouseClickInfo = rightButton().times(2);

  @Test
  void should_Throw_Error_If_MouseClickInfo_Is_Null() {
    MouseClickInfo info = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.clickRow(tree, 1, info));
  }

  @Test
  void should_Click_Row() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tree);
    driver.clickRow(tree, 1, mouseClickInfo);
    recorder.clicked(RIGHT_BUTTON).timesClicked(2);
    assertThat(rowAt(recorder.pointClicked())).isEqualTo(1);
  }

  @Test
  void should_Throw_Error_If_Row_Is_Out_Of_Bounds() {
    showWindow();
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> driver.clickRow(tree, 100, mouseClickInfo), "The given row <100> should be between <0> and <6>");
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Disabled() {
    disableTree();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.clickRow(tree, 1, mouseClickInfo));
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.clickRow(tree, 1, mouseClickInfo));
  }
}
