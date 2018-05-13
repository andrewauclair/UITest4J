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

/**
 * Tests for {@link JProgressBarDriver#requireText(JProgressBar, java.util.regex.Pattern)}.
 *
 * @author Alex Ruiz
 */
class JProgressBarDriver_requireTextAsPattern_Test extends JProgressBarDriver_TestCase {
	@Test
	void should_Pass_If_Text_Matches_Pattern() {
		driver.requireText(progressBar, Pattern.compile("60.*"));
	}

	@Test
	void should_Fail_If_Text_Does_Not_Match_Pattern() {
		Pattern pattern = Pattern.compile("50%");
		ExpectedException.assertOpenTest4jError(() -> driver.requireText(progressBar, pattern), "Expected text of 'TestProgressBar' to match pattern '50%' but was '60%'", pattern, "60%");
	}
}
