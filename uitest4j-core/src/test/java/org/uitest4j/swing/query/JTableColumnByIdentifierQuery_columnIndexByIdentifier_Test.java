/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.query;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.test.core.MethodInvocations.Args.args;

/**
 * Tests for {@link JTableColumnByIdentifierQuery#columnIndexByIdentifier(javax.swing.JTable, Object)}.
 *
 * @author Alex Ruiz
 */
class JTableColumnByIdentifierQuery_columnIndexByIdentifier_Test extends JTableColumnByIdentifierQuery_TestCase {
	private static Collection<Object[]> columnNames() {
		return newArrayList(new Object[][]{{"0"}, {"1"}, {"2"}, {"3"}});
	}

	@ParameterizedTest
	@MethodSource("columnNames")
	void should_Return_Column_Index_Given_Identifier(String identifier) {
		table.startRecording();
		int index = parseInt(identifier);
		assertThat(columnIndexByIdentifier(identifier)).isEqualTo(index);
		table.requireInvoked("getColumn", args(identifier));
	}
}
