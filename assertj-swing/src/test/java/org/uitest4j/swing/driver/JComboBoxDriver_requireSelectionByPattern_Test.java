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

import java.util.regex.Pattern;

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_requireSelectionByPattern_Test extends JComboBoxDriver_TestCase {
  @Test
  void should_Pass_If_JComboBox_Has_Matching_Selection() {
    selectFirstItem();
    driver.requireSelection(comboBox, Pattern.compile("firs."));
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Pass_If_JComboBox_Does_Not_Have_Matching_Selection() {
    selectFirstItem();
    ExpectedException.assertAssertionError(() -> driver.requireSelection(comboBox, Pattern.compile("sec.*")), "selectedIndex", "first", Pattern.compile("sec.*"));
  }

  @Test
  void should_Fail_If_JComboBox_Does_Not_Have_Any_Selection() {
    clearSelection();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(comboBox, Pattern.compile("second")), "property:'selectedIndex'", "No selection");
  }
}
