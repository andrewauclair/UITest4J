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

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_requireSelectionByIndex_Test extends JComboBoxDriver_TestCase {

  @Test
  void should_Pass_If_JComboBox_Has_Expected_Selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, 0);
  }

  @Test
  void should_Fail_If_JComboBox_Does_Not_Have_Expected_Selection() {
    selectFirstItem();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(comboBox, 1), "property:'selectedIndex'", "expected:<[1]> but was:<[0]>");
  }

  @Test
  void should_Fail_If_JComboBox_Does_Not_Have_Any_Selection() {
    clearSelection();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(comboBox, 1), "property:'selectedIndex'", "No selection");
  }
}
