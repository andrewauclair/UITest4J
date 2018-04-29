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

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;
import static org.uitest4j.swing.query.ComponentVisibleQuery.isVisible;

import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.uitest4j.swing.test.recorder.ToolkitClickRecorder;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JListDriver#showPopupMenu(javax.swing.JList, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_showPopupMenuAtItemByIndex_Test extends JListDriver_showPopupMenu_TestCase {
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Show_Popup_Menu_At_Item_With_Given_Index() {
    showWindow();
    driver.click(list);
    ToolkitClickRecorder recorder = clickRecorder.attachToToolkitFor(list);
    driver.showPopupMenu(list, 0);
    recorder.clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
    assertThat(locationToIndex(recorder.pointClicked())).isEqualTo(0);
  }

  @Test
  void should_Throw_Error_If_JList_Is_Disabled() {
    disableList();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.showPopupMenu(list, 0));
  }

  @Test
  void should_Throw_Error_If_JList_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.showPopupMenu(list, "o.*"));
  }
}
