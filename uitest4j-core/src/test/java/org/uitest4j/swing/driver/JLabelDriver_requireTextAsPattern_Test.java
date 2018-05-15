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

import javax.swing.*;
import java.util.regex.Pattern;

/**
 * Tests for {@link JLabelDriver#requireText(JLabel, java.util.regex.Pattern)}.
 *
 * @author Yvonne Wang
 */
class JLabelDriver_requireTextAsPattern_Test extends JLabelDriver_TestCase {
	@Test
	void pases_when_text_matches_expected_pattern() {
		driver.requireText(label, Pattern.compile("H.*"));
	}

	@Test
	void fails_when_text_does_not_match_expected_pattern() {
		Pattern pattern = Pattern.compile("B.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireText(label, pattern), "Expected text of 'TestLabel' to match pattern 'B.*' but was 'Hi'", pattern, "Hi");
	}
}
