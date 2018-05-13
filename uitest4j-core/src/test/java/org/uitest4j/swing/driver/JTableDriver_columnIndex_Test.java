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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link JTableDriver#columnIndex(javax.swing.JTable, Object)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_columnIndex_Test extends JTableDriver_TestCase {
	private static Collection<Object[]> ids() {
		return newArrayList(columnIds());
	}

	@ParameterizedTest
	@MethodSource("ids")
	void should_Return_Column_Index(String columnId) {
		assertThat(driver.columnIndex(table, columnId)).isEqualTo(columnIndexFrom(columnId));
	}
}
