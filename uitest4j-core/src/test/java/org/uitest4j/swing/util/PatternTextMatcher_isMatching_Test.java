/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link PatternTextMatcher#isMatching(String)}.
 *
 * @author Alex Ruiz
 */
class PatternTextMatcher_isMatching_Test {
	@Test
	void should_Throw_Error_If_Any_Pattern_In_Array_Is_Null() {
		Pattern[] patterns = {null, Pattern.compile("hello"), null};
		PatternTextMatcher matcher = new PatternTextMatcher(patterns);
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> matcher.isMatching("hello"));
	}

	@Test
	void should_Return_True_If_Text_Matches_Any_Pattern() {
		PatternTextMatcher matcher = new PatternTextMatcher(Pattern.compile("hello"));
		assertThat(matcher.isMatching("hello")).isTrue();
	}

	@Test
	void should_Return_False_If_Text_Does_Not_Match_Any_Pattern() {
		PatternTextMatcher matcher = new PatternTextMatcher(Pattern.compile("bye"), Pattern.compile("hello"));
		assertThat(matcher.isMatching("world")).isFalse();
	}
}
