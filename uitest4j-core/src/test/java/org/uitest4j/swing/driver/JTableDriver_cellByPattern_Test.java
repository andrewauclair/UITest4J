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
import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.test.ExpectedException;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JTableDriver#cell(javax.swing.JTable, Pattern)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_cellByPattern_Test extends JTableDriver_TestCase {
	@Test
	void should_Find_Cell_Having_Value_That_Matches_Given_Pattern() {
		TableCell cell = driver.cell(table, Pattern.compile("1.*"));
		assertThat(cell.row).isEqualTo(1);
		assertThat(cell.column).isEqualTo(0);
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Throw_Error_If_A_Matching_Cell_Was_Not_Found() {
		ExpectedException.assertContainsMessage(ActionFailedException.class, () -> driver.cell(table, Pattern.compile("Hello World")), "Unable to find cell matching pattern 'Hello World'");
	}
}
