/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.awt.event.InputEvent.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MouseButton#lookup(int)}.
 *
 * @author Alex Ruiz
 */
public class MouseButton_lookup_Test {
	private static Stream<Arguments> arguments() {
		return Stream.of(
				Arguments.of(MouseButton.LEFT_BUTTON, BUTTON1_DOWN_MASK),
				Arguments.of(MouseButton.MIDDLE_BUTTON, BUTTON2_DOWN_MASK),
				Arguments.of(MouseButton.RIGHT_BUTTON, BUTTON3_DOWN_MASK)
		);
	}

	@ParameterizedTest
	@MethodSource("arguments")
	void shouldLookupButtonGivenMask(MouseButton button, int mask) {
		assertThat(MouseButton.lookup(mask)).isEqualTo(button);
	}
}
