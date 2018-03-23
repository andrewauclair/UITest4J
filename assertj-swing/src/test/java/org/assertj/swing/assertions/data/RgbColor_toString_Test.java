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
package org.assertj.swing.assertions.data;

import static org.assertj.swing.assertions.data.RgbColor.color;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link RgbColor#toString()}</code>.
 * 
 * @author Alex Ruiz
 */
class RgbColor_toString_Test {

  private static RgbColor color;

  @BeforeAll
  static void setUpOnce() {
    color = color(0xFF87AB);
  }

  @Test
  void should_Implement_ToString() {
    assertEquals("color[r=255, g=135, b=171]", color.toString());
  }
}
