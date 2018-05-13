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
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.test.builder.JLabels.label;

/**
 * Tests for {@link JLabelMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
class JLabelMatcher_matches_byNameAndTextPattern_withNoMatch_Test extends EDTSafeTestCase {
	private static Collection<Object[]> namesAndText() {
		return newArrayList(new Object[][]{{"someName", Pattern.compile("text")},
				{"name", Pattern.compile("someText")}, {"name", Pattern.compile("text")}});
	}

	@ParameterizedTest
	@MethodSource("namesAndText")
	void should_Return_False_If_Name_Is_Not_Equal_To_Expected_Or_Text_Does_Not_Match_Pattern(String name, Pattern text) {
		JLabelMatcher matcher = JLabelMatcher.withName(name).andText(text);
		JLabel label = label().withName("someName").withText("someText").createNew();
		assertThat(matcher.matches(label)).isFalse();
	}
}
