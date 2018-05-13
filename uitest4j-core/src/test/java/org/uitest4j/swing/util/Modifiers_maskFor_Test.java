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
package org.uitest4j.swing.util;

import org.junit.jupiter.api.Test;

import static java.awt.event.KeyEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Modifiers#maskFor(int)}.
 * 
 * @author Alex Ruiz
 */
class Modifiers_maskFor_Test {
  @Test
  void should_Return_Mask_For_AltGraph_Modifier() {
    assertThat(Modifiers.maskFor(VK_ALT_GRAPH)).isEqualTo(ALT_GRAPH_DOWN_MASK);
  }

  @Test
  void should_Return_Mask_For_Alt_Modifier() {
    assertThat(Modifiers.maskFor(VK_ALT)).isEqualTo(ALT_DOWN_MASK);
  }

  @Test
  void should_Return_Mask_For_Shift_Modifier() {
    assertThat(Modifiers.maskFor(VK_SHIFT)).isEqualTo(SHIFT_DOWN_MASK);
  }

  @Test
  void should_Return_Mask_For_Control_Modifier() {
    assertThat(Modifiers.maskFor(VK_CONTROL)).isEqualTo(CTRL_DOWN_MASK);
  }

  @Test
  void should_Return_Mask_For_Meta_Modifier() {
    assertThat(Modifiers.maskFor(VK_META)).isEqualTo(META_DOWN_MASK);
  }

  @Test
  void should_Throw_Error_If_Key_Is_Not_Modifier() {
    assertThrows(IllegalArgumentException.class, () -> Modifiers.maskFor(VK_A));
  }
}
