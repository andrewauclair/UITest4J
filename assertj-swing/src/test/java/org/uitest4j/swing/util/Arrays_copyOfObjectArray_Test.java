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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Arrays#copyOf(Object[])}.
 * 
 * @author Alex Ruiz
 */
class Arrays_copyOfObjectArray_Test {
  @Test
  void should_Throw_Error_If_Array_To_Copy_Is_Null() {
    Object[] original = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> Arrays.copyOf(original));
  }

  @Test
  void should_Return_Empty_Array_If_Array_To_Copy_Is_Emtpy() {
    assertThat(Arrays.copyOf(new Object[0])).isEmpty();
  }

  @Test
  void should_Return_Copy_Of_Array() {
    Object[] original = { "hello", "bye" };
    Object[] copy = Arrays.copyOf(original);
    assertThat(copy).isEqualTo(original).isNotSameAs(original);
  }
}
