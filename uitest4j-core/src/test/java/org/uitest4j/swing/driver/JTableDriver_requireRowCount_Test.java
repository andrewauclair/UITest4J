/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

import static org.uitest4j.swing.driver.JTableDriver_TestCase.MyWindow.ROW_COUNT;

/**
 * Tests for {@link JTableDriver#requireRowCount(javax.swing.JTable, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_requireRowCount_Test extends JTableDriver_TestCase {
	@Test
	void should_Pass_If_Row_Count_Is_Equal_To_Expected() {
		driver.requireRowCount(table, ROW_COUNT);
	}

	@Test
	void should_Fail_If_Row_Count_Is_Not_Equal_To_Expected() {
		ExpectedException.assertOpenTest4jError(() -> driver.requireRowCount(table, 12), "Expected row count of 'TestTable' to be '12' but was '10'", 12, 10);
	}
}
