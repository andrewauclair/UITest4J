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
 * Tests for {@link Patterns#format(Pattern[])}.
 *
 * @author Alex Ruiz
 */
class Patterns_format_Test {
	@Test
	void should_Throw_Error_If_Pattern_Array_Is_Null() {
		Pattern[] patterns = null;
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> Patterns.format(patterns));
	}

	@Test
	void should_Throw_Error_If_Pattern_In_Array_Is_Null() {
		Pattern[] patterns = {Pattern.compile("hello"), null};
		assertThrows(NullPointerException.class, () -> Patterns.format(patterns));
	}

	@Test
	void should_Format_Pattern_Array() {
		Pattern[] patterns = {Pattern.compile("hello"), Pattern.compile("world")};
		String formatted = Patterns.format(patterns);
		assertThat(formatted).isEqualTo("[hello, world]");
	}
}
