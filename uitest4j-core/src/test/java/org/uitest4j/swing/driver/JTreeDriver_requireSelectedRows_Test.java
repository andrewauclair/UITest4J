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

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTreeDriver#requireSelection(javax.swing.JTree, int[])}.
 *
 * @author Alex Ruiz
 */
class JTreeDriver_requireSelectedRows_Test extends JTreeDriver_selectCell_TestCase {
	@Test
	void should_Throw_Error_If_Array_Of_Indices_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.requireSelection(tree, (int[]) null));
	}

	@Test
	void should_Pass_If_Single_Cell_Is_Selected() {
		selectFirstChildOfRoot();
		int[] rowsToSelect = new int[]{1};
		driver.requireSelection(tree, rowsToSelect);
	}

	@Test
	void should_Fail_If_JTree_Does_Not_Have_Selection() {
		clearTreeSelection();
		ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(tree, new int[]{3}), "property:'selection'", "No selection");
	}

	@Test
	void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
		selectFirstChildOfRoot();
		ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(tree, new int[]{5}), "property:'selection'", "expecting selection:<[5]> but was:<[1]>");
	}
}
