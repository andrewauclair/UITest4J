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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.Collection;

import static java.awt.Color.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link Colors#colorFromHexString(String)}.
 * 
 * @author Alex Ruiz
 */
class Colors_colorFromHexString_Test {
  private static Collection<Object[]> colors() {
    return newArrayList(new Object[][] { { "000000", BLACK }, { "FF0000", RED }, { "00FF00", GREEN },
        { "0000FF", BLUE }, { "FFFF00", YELLOW } });
  }

  @ParameterizedTest
  @MethodSource("colors")
  void should_Return_Color_From_Hex_String(String hexString, Color color) {
    Color actual = Colors.colorFromHexString(hexString);
    assertThat(actual).isEqualTo(color);
  }
}
