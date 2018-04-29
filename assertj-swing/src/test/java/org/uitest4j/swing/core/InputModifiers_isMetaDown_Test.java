/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;

import static java.awt.event.InputEvent.META_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InputModifiers#isMetaDown(int)}.
 * 
 * @author Alex Ruiz
 */
class InputModifiers_isMetaDown_Test {
  @Test
  void should_Return_True_If_Meta_Mask_Is_Present() {
    assertThat(InputModifiers.isMetaDown(META_DOWN_MASK | SHIFT_DOWN_MASK)).isTrue();
  }

  @Test
  void should_Return_False_If_Meta_Mask_Is_Not_Present() {
    assertThat(InputModifiers.isMetaDown(SHIFT_DOWN_MASK)).isFalse();
  }
}
