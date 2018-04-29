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
package org.uitest4j.swing.driver;

import static org.assertj.core.util.Arrays.array;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JFileChooserDriver#selectFiles(JFileChooser, java.io.File[])}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JFileChooserDriver_selectFiles_withInvalidInput_Test extends JFileChooserDriver_withMocks_TestCase {
  @Test
  void should_Throw_Error_If_Array_Of_Files_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.selectFiles(fileChooser, null));
  }

  @Test
  void should_Throw_Error_If_Array_Of_Files_Is_Empty() {
    assertThrows(IllegalArgumentException.class, () -> driver.selectFiles(fileChooser, new File[0]));
  }

  @Test
  void should_Throw_Error_If_Any_File_Is_Null() {
    assertThrows(NullPointerException.class, () -> driver.selectFiles(fileChooser, array(new File("fake"), null)));
  }
}
