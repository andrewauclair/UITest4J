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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.data.TableCell.row;
import static org.uitest4j.swing.test.data.ZeroAndNegativeProvider.zeroAndNegative;

/**
 * Tests for
 * {@link JTableDriver#click(javax.swing.JTable, org.uitest4j.swing.data.TableCell, org.uitest4j.swing.core.MouseButton, int)}
 * .
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_clickCell_withInvalidInput_Test extends JTableDriver_TestCase {
	private static Collection<Object[]> index() {
		return newArrayList(zeroAndNegative());
	}

	@ParameterizedTest
	@MethodSource("index")
	void shouldThrowErrorIfNumberOfTimesToClickCellIsZeroOrNegative(int index) {
		assertThrows(IllegalArgumentException.class, () -> driver.click(table, row(0).column(1), LEFT_BUTTON, index));
	}
}
