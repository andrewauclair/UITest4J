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

import javax.swing.*;
import java.util.regex.Pattern;

import static org.uitest4j.swing.test.swing.JOptionPaneLauncher.pack;

/**
 * Tests for {@link JOptionPaneDriver#requireTitle(JOptionPane, java.util.regex.Pattern)}.
 *
 * @author Alex Ruiz
 */
class JOptionPaneDriver_requireTitleAsPattern_Test extends JOptionPaneDriver_TestCase {
	@Test
	void should_Pass_If_Title_Matches_Pattern() {
		JOptionPane optionPane = informationMessage();
		pack(optionPane, title());
		driver.requireTitle(optionPane, Pattern.compile("JOptionP.*"));
	}

	@Test
	void should_Fail_If_Title_Does_Not_Match_Pattern() {
		JOptionPane optionPane = informationMessage();
		optionPane.setName("TestOptionPane");
		pack(optionPane, title());
		Pattern pattern = Pattern.compile("Yo.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireTitle(optionPane, pattern), "Expected title of 'TestOptionPane' to match pattern 'Yo.*' but was '" + title() + "'", pattern, title());
	}
}
