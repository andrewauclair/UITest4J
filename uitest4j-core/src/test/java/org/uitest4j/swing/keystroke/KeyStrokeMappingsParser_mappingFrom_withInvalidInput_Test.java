/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.keystroke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.ParsingException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KeyStrokeMappingsParser#mappingFrom(String)}.
 *
 * @author Alex Ruiz
 */
class KeyStrokeMappingsParser_mappingFrom_withInvalidInput_Test {
	private KeyStrokeMappingsParser parser;

	@BeforeEach
	void setUp() {
		parser = new KeyStrokeMappingsParser();
	}

	@Test
	void should_Throw_Error_If_Line_Does_Not_Conform_With_Pattern() {
		try {
			parser.mappingFrom("Hello World!");
		}
		catch (ParsingException e) {
			assertThat(e.getMessage()).isEqualTo(
					"Line 'Hello World!' does not conform with pattern '{char}, {keycode}, {modifiers}'");
		}
	}

	@Test
	void should_Throw_Error_If_Char_Cannot_Be_Obtained() {
		try {
			parser.mappingFrom(", COMMA, NO_MASK");
		}
		catch (ParsingException e) {
			assertThat(e.getMessage()).isEqualTo("The text '' should have a single character");
		}
	}

	@Test
	void should_Throw_Error_If_Key_Code_Cannot_Be_Obtained() {
		try {
			parser.mappingFrom("A, SOME_KEY, NO_MASK");
		}
		catch (ParsingException e) {
			assertThat(e.getMessage()).isEqualTo("Unable to retrieve key code from text 'SOME_KEY'");
		}
	}

	@Test
	void should_Throw_Error_If_Modifiers_Cannot_Be_Obtained() {
		try {
			parser.mappingFrom("A, A, SHIFT");
		}
		catch (ParsingException e) {
			assertThat(e.getMessage()).isEqualTo("Unable to retrieve modifiers from text 'SHIFT'");
		}
	}
}
