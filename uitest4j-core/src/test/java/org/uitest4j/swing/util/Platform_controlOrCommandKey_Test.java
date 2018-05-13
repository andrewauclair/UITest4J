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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.META_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link Platform#controlOrCommandKey()}.
 *
 * @author Alex Ruiz
 */
class Platform_controlOrCommandKey_Test extends Platform_TestCase {
	static Collection<Object[]> booleans() {
		return newArrayList(new Object[][]{{CTRL_DOWN_MASK, VK_CONTROL}, {META_DOWN_MASK, VK_META}});
	}

	@ParameterizedTest
	@MethodSource("booleans")
	void should_Return_Control_Or_Command_Key(int mask, int keyCode) {
		when(toolkit.getMenuShortcutKeyMaskEx()).thenReturn(mask);
		assertThat(Platform.controlOrCommandKey()).isEqualTo(keyCode);
	}
}
