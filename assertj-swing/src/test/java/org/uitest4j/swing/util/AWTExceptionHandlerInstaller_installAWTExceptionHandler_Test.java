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
package org.uitest4j.swing.util;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link AWTExceptionHandlerInstaller#installAWTExceptionHandler(Class, SystemPropertyWriter)}.
 * 
 * @author Alex Ruiz
 */
class AWTExceptionHandlerInstaller_installAWTExceptionHandler_Test {
  private SystemPropertyWriter writer;

  @BeforeEach
  void setUp() {
    writer = mock(SystemPropertyWriter.class);
  }

  @Test
  void should_Install_AWT_Event_Handler() {
    Class<CorrectEventHandler> exceptionHandlerType = CorrectEventHandler.class;
    AWTExceptionHandlerInstaller.installAWTExceptionHandler(exceptionHandlerType, writer);
    verify(writer).updateSystemProperty("sun.awt.exception.handler", exceptionHandlerType.getName());
  }

  @Test
  void should_Throw_Error_If_AWT_Event_Handler_Type_Does_Not_Have_Default_Constructor() {
    ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> AWTExceptionHandlerInstaller.installAWTExceptionHandler(WrongEventHandler.class, writer), "The exception handler type should have a public default constructor");
  }

  private static class CorrectEventHandler {
    public CorrectEventHandler() {}
  }

  static class WrongEventHandler {
    public WrongEventHandler(String something) {
      if (something == null) {
        return;
      }
    }
  }
}
