/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core.matcher;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.test.builder.JButtons.button;

/**
 * Tests for {@link JButtonMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JButtonMatcher_matches_byNameAndText_withNotMatch_Test extends EDTSafeTestCase {
	private static Collection<Object[]> namesAndText() {
		return newArrayList(new Object[][]{{"someName", "text"}, {"name", "someText"}, {"name", "text"}});
	}

	@ParameterizedTest
	@MethodSource("namesAndText")
	void should_Return_False_If_Name_Or_Text_Are_Not_Equal_To_Expected(String name, String text) {
		JButtonMatcher matcher = JButtonMatcher.withName(name).andText(text);
		JButton button = button().withName("someName").withText("someText").createNew();
		assertThat(matcher.matches(button)).isFalse();
	}
}
