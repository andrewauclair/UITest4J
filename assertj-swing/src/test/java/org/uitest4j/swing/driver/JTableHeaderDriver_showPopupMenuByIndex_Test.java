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

import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;
import static org.uitest4j.swing.query.ComponentVisibleQuery.isVisible;

/**
 * Tests for {@link JTableHeaderDriver#showPopupMenu(javax.swing.table.JTableHeader, int)}.
 * 
 * @author Yvonne Wang
 */
public class JTableHeaderDriver_showPopupMenuByIndex_Test extends JTableHeaderDriver_showPopupMenu_TestCase {
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Show_Popup_Menu() {
    showWindow();
    driver.click(tableHeader);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tableHeader);
    driver.showPopupMenu(tableHeader, 1);
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    assertThatColumnWasClicked(recorder, 1);
    assertThat(isVisible(popupMenu)).isTrue();
  }
}
