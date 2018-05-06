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

import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link JSliderDriver#slide(javax.swing.JSlider, int)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JSliderDriver_slide_withInvalidInputAndState_Test extends JSliderDriver_TestCase {
  private static Collection<Object[]> allOrientations() {
    return newArrayList(orientations());
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_Value_Is_Less_Than_Minimum(int orientation) {
    setup(orientation);
    showWindow();
    ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.slide(slider, -1), "Value <-1> is not within the JSlider bounds of <0> and <30>");
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_Value_Is_Greater_Than_Maximum(int orientation) {
    setup(orientation);
    showWindow();
    ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.slide(slider, 31), "Value <31> is not within the JSlider bounds of <0> and <30>");
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_JSlider_Is_Disabled(int orientation) {
    setup(orientation);
    disableSlider();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.slide(slider, 6));
  }

  @ParameterizedTest
  @MethodSource("allOrientations")
  void should_Throw_Error_If_JSlider_Is_Not_Showing_On_The_Screen(int orientation) {
    setup(orientation);
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.slide(slider, 6));
  }
}
