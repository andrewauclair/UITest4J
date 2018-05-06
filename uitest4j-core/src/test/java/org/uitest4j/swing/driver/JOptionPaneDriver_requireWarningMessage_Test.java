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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;

import javax.swing.*;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static org.uitest4j.swing.test.swing.JOptionPaneLauncher.pack;

/**
 * Tests for {@link JOptionPaneDriver#requireWarningMessage(JOptionPane)}.
 *
 * @author Alex Ruiz
 */
class JOptionPaneDriver_requireWarningMessage_Test extends JOptionPaneDriver_TestCase {
	@Test
	void should_Pass_If_Error_Type_Is_Equal_To_Expected() {
		JOptionPane optionPane = warningMessage();
		pack(optionPane, title());
		driver.requireWarningMessage(optionPane);
	}

	@RunsInEDT
	private static JOptionPane warningMessage() {
		return messageOfType(WARNING_MESSAGE);
	}

	@Test
	void should_Fail_If_Error_Type_Is_Not_Equal_To_Expected() {
		JOptionPane optionPane = errorMessage();
		pack(optionPane, title());
		ExpectedException.assertOpenTest4jError(() -> driver.requireWarningMessage(optionPane), "Expected message type of 'TestOptionPane' to be 'Warning Message' but was 'Error Message'", "Warning Message", "Error Message");
	}
}
