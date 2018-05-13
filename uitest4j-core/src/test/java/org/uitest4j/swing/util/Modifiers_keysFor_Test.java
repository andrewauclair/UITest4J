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

/**
 * Tests for {@link Modifiers#keysFor(int)}.
 * 
 * @author Alex Ruiz
 */
class Modifiers_keysFor_Test {
  @Test
  void should_Return_Key_For_Alt_Mask() {
    int[] keys = Modifiers.keysFor(ALT_DOWN_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_ALT);
  }

  @Test
  void should_Return_Key_For_AltGraph_Mask() {
    int[] keys = Modifiers.keysFor(ALT_GRAPH_DOWN_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_ALT_GRAPH);
  }

  @Test
  void should_Return_Key_For_Ctrl_Mask() {
    int[] keys = Modifiers.keysFor(CTRL_DOWN_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_CONTROL);
  }

  @Test
  void should_Return_Key_For_Meta_Mask() {
    int[] keys = Modifiers.keysFor(META_DOWN_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_META);
  }

  @Test
  void should_Return_Key_For_Shift_Mask() {
    int[] keys = Modifiers.keysFor(SHIFT_DOWN_MASK);
    assertThat(keys).hasSize(1).containsOnly(VK_SHIFT);
  }

  @Test
  void should_Return_Key_For__More_Than_One_Mask() {
    int[] keys = Modifiers.keysFor(ALT_DOWN_MASK | ALT_GRAPH_DOWN_MASK | CTRL_DOWN_MASK | META_DOWN_MASK | SHIFT_DOWN_MASK);
    assertThat(keys).hasSize(5).containsOnly(VK_ALT, VK_ALT_GRAPH, VK_CONTROL, VK_META, VK_SHIFT);
  }

  @Test
  void shouldNotReturnKeyIs_Mask_Is_Zero() {
    int[] keys = Modifiers.keysFor(0);
    assertThat(keys).isEmpty();
  }
}
