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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.AbstractButtonSelectedQuery.isSelected;

/**
 * Tests for {@link AbstractButtonDriver#select(javax.swing.AbstractButton)}.
 * 
 * @author Alex Ruiz
 */
class AbstractButtonDriver_select_Test extends AbstractButtonDriver_TestCase {
  @Test
  void should_Not_Do_Anything_If_Button_Is_Already_Selected() {
    showWindow();
    selectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  @Test
  void should_Select_Button() {
    showWindow();
    unselectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  private void assertThatCheckBoxIsSelected() {
    assertThat(isSelected(checkBox)).isTrue();
  }

  @Test
  void should_Throw_Error_If_AbstractButton_Disabled() {
    disableCheckBox();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.select(checkBox));
  }

  @Test
  void should_Throw_Error_If_AbstractButton_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.select(checkBox));
  }
}
