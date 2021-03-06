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

/**
 * Tests for {@link JTreeDriver#requireNoSelection(javax.swing.JTree)}.
 *
 * @author Alex Ruiz
 */
class JTreeDriver_requireNoSelection_Test extends JTreeDriver_selectCell_TestCase {
	@Test
	void should_Pass_If_JTree_Does_Not_Have_Selection() {
		clearTreeSelection();
		driver.requireNoSelection(tree);
	}

	@Test
	void should_Fail_If_JTree_Has_Selection() {
		selectFirstChildOfRoot();
		ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireNoSelection(tree), "property:'selection'", "expected no selection but was:<[[root, branch1]]>");
	}
}
