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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static javax.swing.JOptionPane.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link JOptionPaneMessageTypes#messageTypeAsText(int)}.
 *
 * @author Alex Ruiz
 */
class JOptionPaneMessageTypes_messageTypeAsText_Test {
	private static Collection<Object[]> messageTypes() {
		return newArrayList(new Object[][]{{ERROR_MESSAGE, "Error Message"},
				{INFORMATION_MESSAGE, "Information Message"}, {WARNING_MESSAGE, "Warning Message"},
				{QUESTION_MESSAGE, "Question Message"}, {PLAIN_MESSAGE, "Plain Message"}});
	}

	@ParameterizedTest
	@MethodSource("messageTypes")
	void should_Return_Text_For_Given_Message_Type_Value(int messageType, String expected) {
		assertThat(JOptionPaneMessageTypes.messageTypeAsText(messageType)).isEqualTo(expected);
	}
}
