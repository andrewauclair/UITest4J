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

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JComboBoxDriver#requireEditable(javax.swing.JComboBox)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_requireEditable_Test extends JComboBoxDriver_TestCase {
  @Test
  void should_Pass_If_JComboBox_Is_Editable() {
    makeEditable();
    driver.requireEditable(comboBox);
  }

  @Test
  void should_Fail_If_JComboBox_Is_Not_Editable() {
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireEditable(comboBox), "property:'editable'", "expected:<[tru]e> but was:<[fals]e>");
  }
}
