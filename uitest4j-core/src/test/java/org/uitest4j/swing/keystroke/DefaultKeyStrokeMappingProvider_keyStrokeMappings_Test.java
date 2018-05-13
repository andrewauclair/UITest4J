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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.util.Collection;

/**
 * Tests for {@link DefaultKeyStrokeMappingProvider#keyStrokeMappings()}.
 *
 * @author Alex Ruiz
 */
class DefaultKeyStrokeMappingProvider_keyStrokeMappings_Test extends KeyStrokeMappingProvider_TestCase {
	private static Collection<Object[]> keyStrokes() {
		Collection<KeyStrokeMapping> mappings = new DefaultKeyStrokeMappingProvider().keyStrokeMappings();
		return keyStrokesFrom(mappings);
	}

	@ParameterizedTest
	@MethodSource("keyStrokes")
	void should_Provide_Key_Strokes_For_Keyboard(char character, KeyStroke keyStroke) {
		if (basicCharacterVerified(character, keyStroke)) {
			return;
		}
		pressKeyStrokeAndVerify(character, keyStroke);
	}
}
