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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JSlider;

import org.uitest4j.swing.exception.ComponentLookupException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JSliderFixture#JSliderFixture(org.uitest4j.swing.core.Robot, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JSliderFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  void should_Lookup_Showing_JSlider_By_Name() {
    robot.showWindow(window);
    JSliderFixture fixture = new JSliderFixture(robot, "slider");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.slider);
  }

  @Test
  void should_Throw_Error_If_JSlider_With_Matching_Name_Is_Not_Showing() {
    assertThrows(ComponentLookupException.class, () -> new JSliderFixture(robot, "slider"));
  }

  @Test
  void should_Throw_Error_If_A_JSlider_With_Matching_Name_Is_Not_Found() {
    assertThrows(ComponentLookupException.class, () -> new JSliderFixture(robot, "other"));
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JSlider slider = new JSlider(6, 10, 8);

    private MyWindow() {
      super(JSliderFixture_constructor_withRobotAndName_Test.class);
      slider.setName("slider");
      addComponents(slider);
    }
  }
}
