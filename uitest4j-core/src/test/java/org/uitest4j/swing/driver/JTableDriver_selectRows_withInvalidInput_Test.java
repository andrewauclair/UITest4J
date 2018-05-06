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

import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.uitest4j.swing.test.builder.JTables.table;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTableDriver#selectRows(javax.swing.JTable, int...)} using invalid input.
 * 
 * @author Alex Ruiz
 */
public class JTableDriver_selectRows_withInvalidInput_Test extends RobotBasedTestCase {
  private static JTable table;
  private JTableDriver driver;

  @BeforeAll
  public static void setUpOnce() {
    table = table().withRowCount(6).withColumnCount(8).createNew();
  }

  @Override
  protected void onSetUp() {
    driver = new JTableDriver(robot);
  }

  @Test
  void should_Throw_Error_If_Array_Of_Indices_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> driver.selectRows(table, null));
  }

  @Test
  void should_Throw_Error_If_Array_Of_Indices_Is_Empty() {
    assertThrows(IllegalArgumentException.class, () -> driver.selectRows(table, new int[0]));
  }
}
