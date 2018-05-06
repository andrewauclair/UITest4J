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
package org.uitest4j.swing.assertions.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for <code>{@link RgbColor#color(int)}</code>.
 * 
 * @author Yvonne Wang
 */
class RgbColor_color_with_single_RGB_Test {
  @Test
  void should_Decompose_Single_Value_Into_Individual_RGB_Values() {
    RgbColor color = RgbColor.color(0x69FC62);
    assertEquals(105, color.r);
    assertEquals(252, color.g);
    assertEquals(98, color.b);
  }
}
