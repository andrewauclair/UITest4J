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

import org.junit.jupiter.api.Test;

import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KeyStrokeMapping#toString()}.
 *
 * @author Alex Ruiz
 */
class KeyStrokeMapping_toString_Test {
	@Test
	void should_Implement_ToString() {
		KeyStrokeMapping mapping = KeyStrokeMapping.mapping('A', VK_A, SHIFT_DOWN_MASK);
		assertThat(mapping.toString()).isEqualTo("KeyStrokeMapping[character='A', keyStroke=shift pressed A]");
	}
}
