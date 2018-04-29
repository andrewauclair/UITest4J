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
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.uitest4j.swing.test.builder.JComboBoxes.comboBox;

/**
 * Tests for {@link JComboBoxItemIndexPreconditions#checkItemIndexInBounds(JComboBox, int)}.
 * 
 * @author Alex Ruiz
 */
class JComboBoxItemIndexPreconditions_validateIndex_Test extends EDTSafeTestCase {
  @Test
  void should_Not_Throw_Error_If_Index_Is_In_Bounds() {
    JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox, 0);
  }

  @Test
  void should_Throw_Error_If_Index_Is_Negative() {
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox().createNew(), -1), "Item index (-1) should not be less than zero");
  }

  @Test
  void should_Throw_Error_If_JComboBox_Is_Empty() {
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox().createNew(), 0), "JComboBox is empty");
  }

  @Test
  void should_Throw_Error_If_Index_Is_Out_Of_Bounds() {
    JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox, 6), "Item index (6) should be between [0] and [2] (inclusive)");
  }
}
