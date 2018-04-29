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

import org.uitest4j.swing.exception.ParsingException;
import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Collection;

import static java.awt.event.KeyEvent.*;
import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.uitest4j.swing.keystroke.KeyStrokeMapping.mapping;
import static org.uitest4j.swing.keystroke.KeyStrokeMappingProvider.NO_MASK;
import static org.assertj.swing.util.Platform.isWindows;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link KeyStrokeMappingsParser#parse(String)}.
 *
 * @author Alex Ruiz
 */
class KeyStrokeMappingProvider_parse_Test {
  private KeyStrokeMappingsParser parser;

  @BeforeEach
  void setUp() {
    parser = new KeyStrokeMappingsParser();
  }

  @Test
  void should_Throw_Error_If_File_Name_Is_Null() {
    String file = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> parser.parse(file));
  }

  @Test
  void should_Throw_Error_If_File_Name_Is_Empty() {
    assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
  }

  @Test
  void should_Parse_File_In_Classpath() {
    KeyStrokeMappingProvider mappingProvider = parser.parse("keyboard-mapping.txt");
    assertThatContainsDefaultMappings(mappingProvider);
    Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
    assertThat(mappings).contains(mapping('a', VK_A, NO_MASK), mapping('A', VK_A, SHIFT_DOWN_MASK),
                                  mapping(',', VK_COMMA, NO_MASK));
  }

  @Test
  void should_Throw_Error_If_File_Not_Found() {
    ExpectedException.assertContainsMessage(ParsingException.class, () -> parser.parse("abc.txt"), "Unable to open file abc.txt");
  }

  @Test
  void should_Throw_Error_If_File_Is_Null() {
    File file = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> parser.parse(file));
  }

  @Test
  void should_Throw_Error_If_File_Does_Not_Exist() {
    assertThrows(AssertionError.class, () -> parser.parse(new File("abc.xyz")));
  }

  @Test
  void should_Parse_File() throws Exception {
    File f = null;
    try {
      f = createMappingFile("a, A, NO_MASK");
      KeyStrokeMappingProvider mappingProvider = parser.parse(f);
      assertThatContainsDefaultMappings(mappingProvider);
      Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
      assertThat(mappings).contains(mapping('a', VK_A, NO_MASK));
    } finally {
      if (f != null) {
        f.delete();
      }
    }
  }

  private static File createMappingFile(String... mappings) throws IOException {
    File f = newTemporaryFile();

    try (Writer output = new BufferedWriter(new FileWriter(f))) {
      for (String mapping : mappings) {
        output.write(mapping);
        output.write(lineSeparator());
      }
    }
    return f;
  }

  private static void assertThatContainsDefaultMappings(KeyStrokeMappingProvider mappingProvider) {
    Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
    assertThat(mappings).contains(mapping('\b', VK_BACK_SPACE, NO_MASK), mapping('', VK_DELETE, NO_MASK),
                                  mapping('\n', VK_ENTER, NO_MASK), mapping('', VK_ESCAPE, NO_MASK),
                                  mapping('\t', VK_TAB, NO_MASK));
    if (isWindows()) {
      assertThat(mappings).contains(mapping('\r', VK_ENTER, NO_MASK));
    }
  }
}
