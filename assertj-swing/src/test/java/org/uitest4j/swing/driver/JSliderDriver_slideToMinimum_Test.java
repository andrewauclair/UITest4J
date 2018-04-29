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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JSliderDriver#slideToMinimum(javax.swing.JSlider)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JSliderDriver_slideToMinimum_Test extends JSliderDriver_TestCase {
  private static Collection<Object[]> allOrientations() {
    return newArrayList(orientations());
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Slide_To_Maximum(int orientation) {
    setup(orientation);
    showWindow();
    driver.slideToMinimum(slider);
    assertThatSliderValueIs(minimumOf(slider));
  }

  @RunsInEDT
  private static int minimumOf(final JSlider slider) {
    return execute(() -> slider.getMinimum());
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_JSlider_Is_Disabled(int orientation) {
    setup(orientation);
    disableSlider();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.slideToMinimum(slider));
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_JSlider_Is_Not_Showing_On_The_Screen(int orientation) {
    setup(orientation);
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.slideToMinimum(slider));
  }
}
