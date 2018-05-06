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

import java.util.regex.Pattern;

/**
 * Tests for {@link JComboBoxDriver#requireSelection(javax.swing.JComboBox, java.util.regex.Pattern)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_requireSelectionByPattern_Test extends JComboBoxDriver_TestCase {
	@Test
	void should_Pass_If_JComboBox_Has_Matching_Selection() {
		selectFirstItem();
		driver.requireSelection(comboBox, Pattern.compile("firs."));
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Pass_If_JComboBox_Does_Not_Have_Matching_Selection() {
		selectFirstItem();
		Pattern pattern = Pattern.compile("sec.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelection(comboBox, pattern), "Expected selection of 'TestComboBox' to match pattern 'sec.*' but was 'first'", pattern, "first");
	}

	@Test
	void should_Fail_If_JComboBox_Does_Not_Have_Any_Selection() {
		clearSelection();
		Pattern pattern = Pattern.compile("sec.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireSelection(comboBox, pattern), "Expected selection of 'TestComboBox' to match pattern 'sec.*' but had no selection");
	}
}
