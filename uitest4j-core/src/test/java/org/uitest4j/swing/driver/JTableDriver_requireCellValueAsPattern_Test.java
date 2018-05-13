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
import org.uitest4j.swing.test.ExpectedException;

import java.util.regex.Pattern;

import static org.uitest4j.swing.data.TableCell.row;

/**
 * Tests for {@link JTableDriver#requireCellValue(javax.swing.JTable, org.uitest4j.swing.data.TableCell, Pattern)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_requireCellValueAsPattern_Test extends JTableDriver_TestCase {
	@Test
	void should_Pass_If_Cell_Value_Matches_Pattern() {
		driver.requireCellValue(table, row(0).column(0), Pattern.compile("0.*"));
	}

	@Test
	void should_Fail_If_Cell_Value_Does_Not_Match_Pattern() {
//    ExpectedException.assertAssertionError(() -> driver.requireCellValue(table, row(0).column(0), Pattern.compile("0-1")), "value [row=0, column=0]", "0-0", Pattern.compile("0-1"));
		Pattern pattern = Pattern.compile(".*-1");
		ExpectedException.assertOpenTest4jError(() -> driver.requireCellValue(table, row(0).column(0), pattern), "Expected cell at [0, 0] of 'TestTable' to match pattern '.*-1' but was '0-0'", pattern, "0-0");
	}
}
