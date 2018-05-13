/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.data.TableCell;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTableDriver#selectCells(JTable, TableCell[])}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_selectCells_withInvalidInput_Test extends JTableDriver_withMocks_TestCase {
	@Test
	void shouldThrowErrorIfArrayOfCellsToSelectIsNull() {
		TableCell[] cells = null;
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.selectCells(table, cells));
	}

	@Test
	void shouldThrowErrorIfArrayOfCellsToSelectIsEmpty() {
		TableCell[] cells = new TableCell[0];
		assertThrows(IllegalArgumentException.class, () -> driver.selectCells(table, cells));
	}
}
