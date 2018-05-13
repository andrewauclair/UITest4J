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

/**
 * Tests for {@link JListDriver#requireSelection(javax.swing.JList, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_requireSelectionAsText_Test extends JListDriver_TestCase {
	@Test
	void should_Pass_If_Selection_Is_Equal_To_Expected() {
		selectFirstItem();
		driver.requireSelection(list, "one");
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Fail_If_There_Is_No_Selection() {
		clearSelection();
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelection(list, "one"), "Expected selection of 'TestList' to be 'one' but had no selection");
	}

	@Test
	void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
		select(1);
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelection(list, "one"), "Expected selection of 'TestList' to be 'one' but was 'two'", "one", "two");
	}
}
