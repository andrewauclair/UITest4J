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
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.event.KeyEvent;
import java.util.Collection;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.VK_A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.test.awt.TestComponents.singletonComponentMock;

/**
 * Tests for {@link InputModifiers#modifiersMatch(java.awt.event.InputEvent, int)}.
 *
 * @author Alex Ruiz
 */
class InputModifiers_modifiersMatch_Test {
	private static Collection<Object[]> modifiers() {
		return newArrayList(new Object[][]{{ALT_DOWN_MASK}, {ALT_GRAPH_DOWN_MASK}, {CTRL_DOWN_MASK}, {META_DOWN_MASK}, {SHIFT_DOWN_MASK}});
	}

	@ParameterizedTest
	@MethodSource("modifiers")
	void should_Return_True_If_Modifiers_Match(int modifier) {
		KeyEvent e = keyEventWithModifiers(modifier);
		assertThat(InputModifiers.modifiersMatch(e, modifier)).isTrue();
	}

	@ParameterizedTest
	@MethodSource("modifiers")
	void _return_false_if_modifiers_do_not_match(int modifier) {
		KeyEvent e = keyEventWithModifiers(0);
		assertThat(InputModifiers.modifiersMatch(e, modifier)).isFalse();
	}

	private static KeyEvent keyEventWithModifiers(int modifiers) {
		return new KeyEvent(singletonComponentMock(), 0, 0, modifiers, VK_A, 'a');
	}
}
