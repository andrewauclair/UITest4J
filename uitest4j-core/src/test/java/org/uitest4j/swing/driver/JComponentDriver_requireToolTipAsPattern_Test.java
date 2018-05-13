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

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JComponentDriver#requireToolTip(JComponent, java.util.regex.Pattern)}.
 *
 * @author Alex Ruiz
 */
class JComponentDriver_requireToolTipAsPattern_Test extends JComponentDriver_TestCase {
	@Test
	void should_Pass_If_ToolTip_Matches_Pattern() {
		driver.requireToolTip(button, Pattern.compile("A Tool.*"));
	}

	@Test
	void should_Throw_Error_If_Pattern_Is_Null() {
		Pattern p = null;
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.requireToolTip(button, p));
	}

	@Test
	void should_Fail_If_ToolTip_Does_Not_Match_Pattern() {
		Pattern pattern = Pattern.compile("Hel.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireToolTip(button, pattern), "Expected tooltip of 'TestButton' to match pattern 'Hel.*' but was 'A ToolTip'", pattern, "A ToolTip");
	}
}
