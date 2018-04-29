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
package org.uitest4j.swing.format;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.swing.test.builder.JComboBoxes.comboBox;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link IntrospectionComponentFormatter#format(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
class IntrospectionComponentFormatter_format_withInvalidInput_Test extends EDTSafeTestCase {
  private IntrospectionComponentFormatter formatter;

  @BeforeEach
  void setUp() {
    formatter = new IntrospectionComponentFormatter(JButton.class, "name", "text");
  }

  @Test
  void should_Throw_Error_If_Component_Belongs_To_Unsupported_Type() {
    assertThrows(IllegalArgumentException.class, () -> formatter.format(comboBox().createNew()));
  }

  @Test
  void should_Throw_Error_If_Component_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> formatter.format(null));
  }
}
