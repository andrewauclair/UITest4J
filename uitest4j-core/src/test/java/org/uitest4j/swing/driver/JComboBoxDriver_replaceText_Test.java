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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JComboBoxDriver#replaceText(javax.swing.JComboBox, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_replaceText_Test extends JComboBoxDriver_TestCase {
  @Test
  void should_Replace_Text() {
    showWindow();
    makeEditableAndSelectFirstItem();
    driver.replaceText(comboBox, "Hello");
    assertThat(textIn(comboBox)).isEqualTo("Hello");
  }

  @Test
  void should_Replace_Text_With_Empty_String() {
    showWindow();
    makeEditableAndSelectFirstItem();
    driver.replaceText(comboBox, "");
    assertThat(textIn(comboBox)).isEqualTo("");
  }

  @Test
  void should_Throw_Error_If_JComboBox_Is_Disabled() {
    disableComboBox();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.replaceText(comboBox, "Hello"));
  }

  @Test
  void should_Throw_Error_If_JComboBox_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.replaceText(comboBox, "Hello"));
  }

  @Test
  void should_Throw_Error_If_JComboBox_Is_Not_Editable() {
    showWindow();
    assertThatIllegalStateExceptionCauseIsNotEditableComboBox(() -> driver.replaceText(comboBox, "Hello"));
  }
}
