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

import static org.assertj.core.util.Arrays.array;

/**
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, String...)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_requireSelectedItemsAsText_Test extends JListDriver_TestCase {
	@Test
	void should_Pass_If_Selection_Is_Equal_To_Expected() {
		select(0, 1);
		driver.requireSelectedItems(list, "two", "one");
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Pass_If_Selection_Matches_Pattern() {
		select(1, 2);
		driver.requireSelectedItems(list, "t.*");
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Pass_If_Selection_Matches_Patterns() {
		select(0, 1);
		driver.requireSelectedItems(list, "tw.*", "o.*");
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Fail_If_There_Is_No_Selection() {
		clearSelection();
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelectedItems(list, "one", "two"), "Expected selected items of 'TestList' to contain [one, two] but had no selection");
	}

	@Test
	void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
		select(2);
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelectedItems(list, "one"), "Expected selected items of 'TestList' to contain [one]. Selected items were [three]", array("one"), array("three"));
	}
}
