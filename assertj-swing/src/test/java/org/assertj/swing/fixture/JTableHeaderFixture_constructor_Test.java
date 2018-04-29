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
package org.assertj.swing.fixture;

import static org.uitest4j.swing.core.TestRobots.singletonRobotMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import javax.swing.table.JTableHeader;

import org.assertj.swing.exception.ComponentLookupException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTableHeaderFixture#JTableHeaderFixture(org.uitest4j.swing.core.Robot, JTableHeader)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JTableHeaderFixture_constructor_Test {
  @Test
  void should_Throw_Error_If_Robot_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> new JTableHeaderFixture(null, mock(JTableHeader.class)));
  }

  @Test
  void should_Throw_Error_If_Target_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> new JTableHeaderFixture(singletonRobotMock(), null));
  }
}
