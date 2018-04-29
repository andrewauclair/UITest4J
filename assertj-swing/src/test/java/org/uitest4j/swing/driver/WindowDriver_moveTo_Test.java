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

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link WindowDriver#moveTo(java.awt.Window, java.awt.Point)}.
 * 
 * @author Alex Ruiz
 */
class WindowDriver_moveTo_Test extends WindowDriver_TestCase {
  @Test
  void should_Move_Window() {
    showWindow();
    Point newPosition = new Point(200, 200);
    driver.moveTo(window, newPosition);
    assertThat(locationOnScreen(window)).isEqualTo(newPosition);
  }

  @Test
  void should_Throw_Error_If_Window_Is_Disabled() {
    disableWindow();
    Throwable exception = assertThrows(IllegalStateException.class, () -> driver.moveTo(window, new Point(100, 100)));
    assertTrue(exception.getMessage().contains("Expecting component"));
    assertTrue(exception.getMessage().contains("to be enabled"));
  }

  @Test
  void should_Throw_Error_If_Window_Is_Not_Showing_On_The_Screen() {
    Throwable exception = assertThrows(IllegalStateException.class, () -> driver.moveTo(window, new Point(100, 100)));
    assertTrue(exception.getMessage().contains("Expecting component"));
    assertTrue(exception.getMessage().contains("to be showing on the screen"));
  }
}
