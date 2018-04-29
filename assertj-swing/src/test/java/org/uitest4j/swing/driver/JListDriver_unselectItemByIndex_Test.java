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
import static org.uitest4j.swing.util.Platform.controlOrCommandKey;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JListDriver#unselectItem(javax.swing.JList, int)}.
 *
 * @author Christian Rösch
 */
class JListDriver_unselectItemByIndex_Test extends JListDriver_TestCase {
  @Test
  void should_Unselect_Item() {
    showWindow();
    driver.selectItem(list, 2);
    assertThat(selectedValue()).isEqualTo("three");
    int key = controlOrCommandKey();
    robot.pressKey(key);
    try {
      driver.unselectItem(list, 2);
    } finally {
      robot.releaseKey(key);
    }
    assertThat(selectedValue()).isNull();
  }

  @Test
  void should_Not_Unselect_Item_If_Already_Unselected() {
    showWindow();
    driver.unselectItem(list, 1);
    assertThat(selectedValue()).isNull();
  }

  @Test
  void should_Throw_Error_If_JList_Is_Disabled() {
    disableList();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.unselectItem(list, 2));
  }

  @Test
  void should_Throw_Error_If_JList_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.unselectItem(list, 2));
  }
}
