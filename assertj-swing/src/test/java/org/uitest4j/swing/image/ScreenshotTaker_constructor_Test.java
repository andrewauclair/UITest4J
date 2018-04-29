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
package org.uitest4j.swing.image;

import org.assertj.swing.util.RobotFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.image.TestImageFileWriters.singletonImageFileWriterMock;
import static org.assertj.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ScreenshotTaker#ScreenshotTaker(ImageFileWriter, RobotFactory)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ScreenshotTaker_constructor_Test {
  private ImageFileWriter writer;
  private RobotFactory robotFactory;
  private AWTException toThrow;

  @BeforeEach
  void setUp() {
    writer = singletonImageFileWriterMock();
    robotFactory = newRobotFactoryMock();
    toThrow = new AWTException("Thrown on purpose");
  }

  @Test
  void should_Throw_Wrapped_Exception_Thrown_When_Creating_Robot() throws AWTException {
    when(robotFactory.newRobotInLeftScreen()).thenThrow(toThrow);
    Throwable exception = assertThrows(ImageException.class, () -> new ScreenshotTaker(writer, robotFactory));
    assertThat(exception.getCause()).isEqualTo(toThrow);
  }
}
