/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

import java.util.regex.Pattern;

import static org.assertj.core.util.Arrays.array;

/**
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, java.util.regex.Pattern...)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_requireSelectedItemsAsPattern_Test extends JListDriver_TestCase {
	@Test
	void should_Pass_If_Selection_Is_Equal_To_Expected() {
		select(0, 1);
		driver.requireSelectedItems(list, Pattern.compile("two"), Pattern.compile("one"));
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Pass_If_Selection_Matches_Pattern() {
		select(1, 2);
		driver.requireSelectedItems(list, Pattern.compile("t.*"));
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Pass_If_Selection_Matches_Patterns() {
		select(0, 1);
		driver.requireSelectedItems(list, Pattern.compile("tw.*"), Pattern.compile("o.*"));
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Fail_If_There_Is_No_Selection() {
		clearSelection();
		Pattern one = Pattern.compile("on.*");
		Pattern two = Pattern.compile("tw.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelectedItems(list, one, two), "Expected selected items of 'TestList' to match patterns [on.*, tw.*] but had no selection");
	}

	@Test
	void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
		select(2);
		Pattern pattern = Pattern.compile("on.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelectedItems(list, pattern), "Expected selected items of 'TestList' to match patterns [on.*]. Selected items were [three]", array(pattern), array("three"));
	}
}
