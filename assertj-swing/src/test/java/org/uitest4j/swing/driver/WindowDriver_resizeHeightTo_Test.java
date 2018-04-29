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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.query.ComponentSizeQuery.sizeOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.awt.FluentDimension;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WindowDriver#resizeHeightTo(java.awt.Window, int)}.
 * 
 * @author Alex Ruiz
 */
class WindowDriver_resizeHeightTo_Test extends WindowDriver_TestCase {
  @Test
  void should_Resize_Height() {
    showWindow();
    Dimension newSize = new FluentDimension(sizeOf(window)).addToHeight(100);
    driver.resizeHeightTo(window, newSize.height);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  @Test
  void should_Throw_Error_If_Window_Is_Disabled() {
    disableWindow();
    Throwable exception = assertThrows(IllegalStateException.class, () -> driver.resizeHeightTo(window, 10));
    assertTrue(exception.getMessage().contains("Expecting component"));
    assertTrue(exception.getMessage().contains("to be enabled"));
  }

  @Test
  void should_Throw_Error_If_Window_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.resizeHeightTo(window, 10));
  }

  @Test
  void should_Throw_Error_If_Window_Is_Not_Resizable() {
    makeWindowNotResizable();
    showWindow();
    assertThrows(IllegalStateException.class, () -> driver.resizeHeightTo(window, 10));
  }
}
