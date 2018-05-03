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

import static org.uitest4j.swing.data.TableCell.row;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTableDriver#requireNotEditable(javax.swing.JTable, org.uitest4j.swing.data.TableCell)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_requireNotEditable_Test extends JTableDriver_TestCase {
  @Test
  void should_Pass_If_Cell_Is_Not_Editable() {
    makeFirstCellNotEditable();
    driver.requireNotEditable(table, row(0).column(0));
  }

  @Test
  void should_Fail_If_Cell_Is_Editable() {
    makeFirstCellEditable();
//	  ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireNotEditable(table, row(0).column(0)), "property:'editable [0, 0]'", "expected:<[fals]e> but was:<[tru]e>");
    ExpectedException.assertOpenTest4jError(() -> driver.requireNotEditable(table, row(0).column(0)), "Expected cell [0, 0] of 'TestTable' to not be editable");
  }
}
