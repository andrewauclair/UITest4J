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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

import static java.awt.event.InputEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link InputModifiers}.
 * 
 * @author Alex Ruiz
 */
class InputModifiers_unify_Test {
  private static Collection<Object[]> modifiers() {
    return newArrayList(new Object[][] { { new int[] { ALT_DOWN_MASK, ALT_GRAPH_DOWN_MASK, CTRL_DOWN_MASK, META_DOWN_MASK, SHIFT_DOWN_MASK } },
        { new int[] { ALT_DOWN_MASK, ALT_GRAPH_DOWN_MASK, CTRL_DOWN_MASK, META_DOWN_MASK } },
        { new int[] { ALT_DOWN_MASK, ALT_GRAPH_DOWN_MASK, CTRL_DOWN_MASK } }, { new int[] { ALT_DOWN_MASK, ALT_GRAPH_DOWN_MASK } },
        { new int[] { ALT_DOWN_MASK } } });
  }

  @ParameterizedTest
  @MethodSource("modifiers")
  void should_Unify_Modifiers(int[] modifiers) {
    int unified = InputModifiers.unify(modifiers);
    // asserts that contains only the passed modifiers
    for (int modifier : modifiers) {
      assertThat((unified & modifier) != 0).isTrue();
    }
    // asserts that does not contain modifiers that were not passed
    for (int modifier : missingModifiersIn(modifiers)) {
      assertThat((unified & modifier) != 0).isFalse();
    }
  }

  private List<Integer> missingModifiersIn(int[] existingModifiers) {
    List<Integer> allModifiers = allModifiers();
    for (Integer modifier : existingModifiers) {
      allModifiers.remove(modifier);
    }
    return allModifiers;
  }

  private List<Integer> allModifiers() {
    return newArrayList(ALT_DOWN_MASK, ALT_GRAPH_DOWN_MASK, CTRL_DOWN_MASK, META_DOWN_MASK, SHIFT_DOWN_MASK);
  }
}
