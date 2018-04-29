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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.format.Formatting.format;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Component;

import javax.swing.JButton;

/**
 * Tests for {@link BasicComponentPrinter#printComponents(java.io.PrintStream, Class, java.awt.Container)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicComponentPrinter_printComponentsByTypeInRoot_Test extends BasicComponentPrinter_TestCase {
  @Test
  void should_Throw_Error_If_OutputStream_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> printer.printComponents(null, JButton.class, windowOne));
  }

  @Test
  void should_Throw_Error_If_Type_To_Match_Is_Null() {
    Class<? extends Component> type = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> printer.printComponents(out, type, windowOne));
  }

  @Test
  void should_Print_All_Components_Of_Given_Type_In_Given_Root() {
    printer.printComponents(out, JButton.class, windowOne);
    assertThat(out.printed()).containsOnly(format(windowOne.button));
  }
}
