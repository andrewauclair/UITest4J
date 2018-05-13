/*
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
 * Tests for
 * {@link JTabbedPaneDriver#requireTabToolTipText(javax.swing.JTabbedPane, Pattern, int)}
 *
 * @author William Bakker
 */
class JTabbedPaneDriver_requireTabToolTipTextAsPattern_Test extends JTabbedPaneDriver_TestCase {
	@Test
	void should_Fail_If_ToolTipText_Does_Not_Match_Pattern() {
		Pattern pattern = Pattern.compile("He.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireTabToolTipText(tabbedPane, pattern, 0),
				"Expected tooltip text of tab at index 0 of 'TestTabbedPane' to match pattern 'He.*' but was 'tip1'",
				pattern, "tip1");
	}

	@Test
	void should_Pass_If_ToolTipText_Matches_Pattern() {
		driver.requireTabToolTipText(tabbedPane, Pattern.compile("t.*"), 0);
	}
}
