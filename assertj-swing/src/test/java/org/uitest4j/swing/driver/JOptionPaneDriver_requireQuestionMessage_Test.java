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

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static org.uitest4j.swing.test.swing.JOptionPaneLauncher.pack;

/**
 * Tests for {@link JOptionPaneDriver#requireQuestionMessage(JOptionPane)}.
 *
 * @author Alex Ruiz
 */
class JOptionPaneDriver_requireQuestionMessage_Test extends JOptionPaneDriver_TestCase {
	@Test
	void should_Pass_If_Error_Type_Is_Equal_To_Expected() {
		JOptionPane optionPane = questionMessage();
		pack(optionPane, title());
		driver.requireQuestionMessage(optionPane);
	}

	@RunsInEDT
	private static JOptionPane questionMessage() {
		return messageOfType(QUESTION_MESSAGE);
	}

	@Test
	void should_Fail_If_Error_Type_Is_Not_Equal_To_Expected() {
		JOptionPane optionPane = errorMessage();
		pack(optionPane, title());
		ExpectedException.assertOpenTest4jError(() -> driver.requireQuestionMessage(optionPane), "Expected message type of 'TestOptionPane' to be 'Question Message' but was 'Error Message'", "Question Message", "Error Message");
	}
}
