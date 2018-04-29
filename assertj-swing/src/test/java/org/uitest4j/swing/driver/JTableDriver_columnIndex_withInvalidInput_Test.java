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

import org.uitest4j.swing.exception.ActionFailedException;
import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTableDriver#columnIndex(javax.swing.JTable, Object)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_columnIndex_withInvalidInput_Test extends JTableDriver_TestCase {
  @Test
  void should_Throw_Error_If_Column_With_Given_Id_Was_Not_Found() {
    ExpectedException.assertContainsMessage(ActionFailedException.class, () -> driver.columnIndex(table, "Hello World"), "Unable to find a column with id 'Hello World");
  }
}
