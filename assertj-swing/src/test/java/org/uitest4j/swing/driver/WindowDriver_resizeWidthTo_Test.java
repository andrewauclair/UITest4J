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

import java.awt.Dimension;

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.awt.FluentDimension;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WindowDriver#resizeWidthTo(java.awt.Window, int)}.
 * 
 * @author Alex Ruiz
 */
class WindowDriver_resizeWidthTo_Test extends WindowDriver_TestCase {
  @Test
  void should_Resize_Width() {
    showWindow();
    Dimension newSize = new FluentDimension(sizeOf(window)).addToWidth(200);
    driver.resizeWidthTo(window, newSize.width);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  @Test
  void should_Throw_Error_If_Window_Is_Disabled() {
    disableWindow();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.resizeWidthTo(window, 10));
  }

  @Test
  void should_Throw_Error_If_Window_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.resizeWidthTo(window, 10));
  }

  @Test
  void should_Throw_Error_If_Window_Is_Not_Resizable() {
    makeWindowNotResizable();
    showWindow();
    assertThrows(IllegalStateException.class, () -> driver.resizeWidthTo(window, 10));
  }
}
