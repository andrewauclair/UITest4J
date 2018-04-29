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
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JListItemPreconditions#checkIndexInBounds(JList, int)}.
 * 
 * @author Alex Ruiz
 */
class JListItemIndexValidator_validateIndices_Test extends JListItemIndexValidator_TestCase {
  @Test
  void should_Pass_If_Indices_Are_Valid() {
    validateIndices(0, 1, 2);
  }

  @Test
  void should_Throw_Error_If_Index_Is_Negative() {
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> validateIndices(-1, 0, 1), "Item index (-1) should be between [0] and [2] (inclusive)");
  }

  @Test
  void should_Throw_Error_If_Index_Is_Greater_Than_Index_Of_Last_Item() {
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> validateIndices(0, 1, 3), "Item index (3) should be between [0] and [2] (inclusive)");
  }

  @RunsInEDT
  private void validateIndices(int... indices) {
    validateIndices(list, indices);
  }

  @RunsInEDT
  private static void validateIndices(final JList list, final int... indices) {
    execute(() -> JListItemPreconditions.checkIndicesInBounds(list, indices));
  }
}
