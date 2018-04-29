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

import org.uitest4j.swing.exception.UnexpectedException;
import org.assertj.swing.util.RobotFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.Robot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link RobotEventGenerator#RobotEventGenerator(org.assertj.swing.util.RobotFactory, Settings)}.
 * 
 * @author Alex Ruiz
 */
class RobotEventGenerator_constructorWithRobotFactory_Test {
  private RobotFactory robotFactory;

  @BeforeEach
  void setUp() {
    robotFactory = newRobotFactoryMock();
  }

  @Test
  void should_Use_RobotFactory_To_Create_AWTRobot() throws AWTException {
    Robot robot = mock(Robot.class);
    when(robotFactory.newRobotInLeftScreen()).thenReturn(robot);
    RobotEventGenerator eventGenerator = new RobotEventGenerator(robotFactory, new Settings());
    assertThat(eventGenerator.robot()).isSameAs(robot);
  }

  @Test
  void should_Rethrow_Any_Error_From_RobotFactory() throws AWTException {
    AWTException toThrow = new AWTException("Thrown on purpose");
    when(robotFactory.newRobotInLeftScreen()).thenThrow(toThrow);
    try {
      assertThrows(UnexpectedException.class, () -> new RobotEventGenerator(robotFactory, new Settings()));
    } catch (UnexpectedException e) {
      assertThat(e.getCause()).isSameAs(toThrow);
      throw e;
    }
  }
}
