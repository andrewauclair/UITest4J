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

import org.uitest4j.swing.cell.JTableCellReader;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;

import static org.uitest4j.swing.core.TestRobots.singletonRobotMock;
import static org.uitest4j.swing.test.builder.JTables.table;
import static org.mockito.Mockito.mock;

/**
 * Base test case that uses a mock implementation of {@link JTableCellReader}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTableDriver_withMockCellReader_TestCase extends EDTSafeTestCase {
  JTableDriver driver;
  JTableCellReader cellReader;
  static JTable table;

  @BeforeAll
  public static void setUpTable() {
    table = table().withRowCount(1).withColumnCount(6).createNew();
  }

  @BeforeEach
  public final void setUp() {
    driver = new JTableDriver(singletonRobotMock());
    cellReader = mock(JTableCellReader.class);
    driver.replaceCellReader(cellReader);
    onSetUp();
  }

  void onSetUp() {
  }
}
