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

/**
 * Tests for {@link JTreeDriver#unselectRow(javax.swing.JTree, int)}.
 *
 * @author Christian Rösch
 */
class JTreeDriver_unselectRow_withInvalidIndex_Test extends JTreeDriver_TestCase {
	private static Collection<Object[]> invalidRows() {
		return newArrayList(outOfBoundRowIndices());
	}

	@ParameterizedTest
	@MethodSource("invalidRows")
	void should_Throw_Error_If_Given_Row_Index_Is_Out_Of_Bounds(int invalidRow) {
		showWindow();
		assertThrows(IndexOutOfBoundsException.class, () -> driver.unselectRow(tree, invalidRow));
	}
}
