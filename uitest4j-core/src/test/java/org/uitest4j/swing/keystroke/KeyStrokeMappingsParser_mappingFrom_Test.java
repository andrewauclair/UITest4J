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
package org.uitest4j.swing.keystroke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_COMMA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.keystroke.KeyStrokeMapping.mapping;
import static org.uitest4j.swing.keystroke.KeyStrokeMappingProvider.NO_MASK;

/**
 * Tests for {@link KeyStrokeMappingsParser#mappingFrom(String)}.
 * 
 * @author Alex Ruiz
 */
class KeyStrokeMappingsParser_mappingFrom_Test {
  private static Collection<Object[]> linesToParse() {
    return newArrayList(new Object[][] { { "a, A, NO_MASK", mapping('a', VK_A, NO_MASK) },
        { "A, A, SHIFT_MASK", mapping('A', VK_A, SHIFT_DOWN_MASK) }, { "COMMA, COMMA, NO_MASK", mappingForComma() },
        { "COMMA,COMMA,NO_MASK", mappingForComma() }, { "  COMMA,  COMMA,  NO_MASK", mappingForComma() }, });
  }

  private static KeyStrokeMapping mappingForComma() {
    return mapping(',', VK_COMMA, NO_MASK);
  }

  private KeyStrokeMappingsParser parser;

  @BeforeEach
  void setUp() {
    parser = new KeyStrokeMappingsParser();
  }

  @ParameterizedTest
  @MethodSource("linesToParse")
  void should_Create_Mapping_From_Line(String lineToParse, KeyStrokeMapping expectedMapping) {
    KeyStrokeMapping parsedMapping = parser.mappingFrom(lineToParse);
    assertThat(parsedMapping).isEqualTo(expectedMapping);
  }
}
