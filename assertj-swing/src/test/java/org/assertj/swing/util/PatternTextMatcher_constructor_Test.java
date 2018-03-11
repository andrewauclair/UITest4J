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
package org.assertj.swing.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link PatternTextMatcher#PatternTextMatcher(java.util.regex.Pattern...)}.
 * 
 * @author Alex Ruiz
 */
class PatternTextMatcher_constructor_Test {
  @Test
  void should_Throw_Error_If_Pattern_Array_Is_Null() {
    Pattern[] patterns = null;
    assertThrows(IllegalArgumentException.class, () -> new PatternTextMatcher(patterns));
  }

  @Test
  void should_Throw_Error_If_Pattern_Array_Is_Empty() {
    assertThrows(IllegalArgumentException.class, () -> new PatternTextMatcher(new Pattern[0]));
  }
}
