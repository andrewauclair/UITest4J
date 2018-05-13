/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.core.TestRobots.singletonRobotMock;

/**
 * Tests for {@link JTableDriver#replaceCellReader(org.uitest4j.swing.cell.JTableCellReader)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableDriver_cellReader_Test {
	private JTableDriver driver;

	@BeforeEach
	void setUp() {
		driver = new JTableDriver(singletonRobotMock());
	}

	@Test
	void should_Throw_Error_If_CellReader_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.replaceCellReader(null));
	}
}
