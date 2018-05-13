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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.uitest4j.swing.assertions.data.RgbColor.color;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for <code>{@link RgbColor#isEqualTo(RgbColor, int)}</code>.
 * 
 * @author Yvonne Wang
 */
class RgbColor_isEqualTo_Test {

  private static RgbColor color;

  @BeforeAll
  static void setUpOnce() {
    color = color(0x69FC62);
  }

  @Test
  void should_Return_True_If_Both_RGBColor_Are_Exactly_Equal() {
    assertTrue(color.isEqualTo(color(0x69FC62), 1));
  }

  @Test
  void should_Return_True_If_Difference_In_Components_Is_Less_Or_Equal_Than_Offset() {
    assertTrue(color.isEqualTo(color(0x68FB61), 1));
  }

  @Test
  void should_Return_False_If_Difference_In_Red_Is_Greater_Than_Offset() {
    assertFalse(color.isEqualTo(color(0x67FC62), 1));
  }

  @Test
  void should_Return_False_If_Difference_In_Green_Greater_Than_Offset() {
    assertFalse(color.isEqualTo(color(0x69FA62), 1));
  }

  @Test
  void should_Return_False_If_Difference_In_Blue_Is_Greater_Than_Offset() {
    assertFalse(color.isEqualTo(color(0x69FC60), 1));
  }

  @Test
  void should_Return_False_If_Other_RGBColor_Is_Null() {
    assertFalse(color.isEqualTo(null, 1));
  }
}
