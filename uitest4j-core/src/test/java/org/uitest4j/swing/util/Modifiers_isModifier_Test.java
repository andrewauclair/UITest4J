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

import static java.awt.event.KeyEvent.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Modifiers#isModifier(int)}.
 *
 * @author Alex Ruiz
 */
class Modifiers_isModifier_Test {
	@Test
	void should_Return_True_If_Key_Is_AltGraph_Modifier() {
		assertThat(Modifiers.isModifier(VK_ALT_GRAPH)).isTrue();
	}

	@Test
	void should_Return_True_If_Key_Is_Alt_Modifier() {
		assertThat(Modifiers.isModifier(VK_ALT)).isTrue();
	}

	@Test
	void should_Return_True_If_Key_Is_Shift_Modifier() {
		assertThat(Modifiers.isModifier(VK_SHIFT)).isTrue();
	}

	@Test
	void should_Return_True_If_Key_Is_Control_Modifier() {
		assertThat(Modifiers.isModifier(VK_CONTROL)).isTrue();
	}

	@Test
	void should_Return_True_If_Key_Is_Meta_Modifier() {
		assertThat(Modifiers.isModifier(VK_META)).isTrue();
	}

	@Test
	void should_Return_False_If_Given_Key_Is_Not_Modifier() {
		assertThat(Modifiers.isModifier(VK_A)).isFalse();
	}
}
