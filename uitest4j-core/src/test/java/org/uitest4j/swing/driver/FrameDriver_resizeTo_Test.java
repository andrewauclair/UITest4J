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

import java.awt.Dimension;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WindowDriver#resizeTo(java.awt.Window, java.awt.Dimension)} to ensure it works with
 * {@link FrameDriver}.
 * 
 * @author Alex Ruiz
 */
class FrameDriver_resizeTo_Test extends FrameDriver_TestCase {
  @Test
  void should_Resize_Frame() {
    showWindow();
    Dimension newSize = windowSize().addToWidth(20).addToHeight(40);
    driver.resizeTo(window, newSize);
    assertThat(windowSize()).isEqualTo(newSize);
  }

  @Test
  void should_Throw_Error_If_Frame_Is_Disabled() {
    disableWindow();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.resizeTo(window, new Dimension(10, 10)));
  }

  @Test
  void should_Throw_Error_If_Frame_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.resizeTo(window, new Dimension(10, 10)));
  }

  @Test
  void should_Throw_Error_If_Frame_Is_Not_Resizable() {
    makeWindowNotResizable();
    ExpectedException.assertIllegalStateIsNotResizableComponent(() -> driver.resizeTo(window, new Dimension(10, 10)));
  }
}
