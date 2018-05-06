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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, String...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_requireSelectedItemsAsText_withInvalidInput_Test extends JListDriver_withMocks_TestCase {
  @Test
  void should_Throw_Error_If_Array_Of_Values_Is_Null() {
    String[] values = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.requireSelectedItems(list, values));
  }

  @Test
  void should_Throw_Error_If_Array_Of_Values_Is_Empty() {
    String[] values = new String[0];
    assertThrows(IllegalArgumentException.class, () -> driver.requireSelectedItems(list, values));
  }
}
