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

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JSliderDriver#slideToMaximum(javax.swing.JSlider)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JSliderDriver_slideToMaximum_Test extends JSliderDriver_TestCase {
  private static Collection<Object[]> allOrientations() {
    return newArrayList(orientations());
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Slide_To_Maximum(int orientation) {
    setup(orientation);
    showWindow();
    driver.slideToMaximum(slider);
    assertThatSliderValueIs(maximumOf(slider));
  }

  @RunsInEDT
  private static int maximumOf(final JSlider slider) {
    return execute(() -> slider.getMaximum());
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_JSlider_Is_Disabled(int orientation) {
    setup(orientation);
    disableSlider();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.slideToMaximum(slider));
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_JSlider_Is_Not_Showing_On_The_Screen(int orientation) {
    setup(orientation);
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.slideToMaximum(slider));
  }
}
