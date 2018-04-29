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
 * Tests for {@link JTreeDriver#checkRowInBounds(JTree, int)}.
 * 
 * @author Alex Ruiz
 */
class JTreeDriver_validateRow_Test extends JTreeDriver_TestCase {
  @Test
  void should_Pass_If_Index_Is_Valid() {
    driver.checkRowInBounds(tree, 1);
  }

  @Test
  void should_Throw_Error_If_Index_Is_Out_Of_Bounds() {
    assertThrows(IndexOutOfBoundsException.class, () -> driver.checkRowInBounds(tree, -1));
  }
}
