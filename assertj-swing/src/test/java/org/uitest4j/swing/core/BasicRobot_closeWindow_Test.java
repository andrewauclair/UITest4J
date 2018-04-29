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
package org.uitest4j.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.query.ComponentShowingQuery.isShowing;
import static org.uitest4j.swing.query.ComponentVisibleQuery.isVisible;
import static org.assertj.swing.test.task.ComponentSetVisibleTask.hide;
import static org.assertj.swing.timing.Pause.pause;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.assertj.swing.timing.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link BasicRobot#close(java.awt.Window)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_closeWindow_Test extends EDTSafeTestCase {
  private BasicRobot robot;
  private TestWindow w;

  @BeforeEach
  void setUp() {
    robot = (BasicRobot) BasicRobot.robotWithNewAwtHierarchy();
    w = TestWindow.createNewWindow(getClass());
    w.display();
  }

  @AfterEach
  void tearDown() {
    robot.cleanUp();
  }

  @Test
  void should_Close_Window() {
    robot.close(w);
    pause(new Condition("Window closed") {
      @Override
      public boolean test() {
        return !isVisible(w);
      }
    });
    assertThat(isVisible(w)).isFalse();
  }

  @Test
  void should_Not_Close_Window_If_Window_Not_Showing() {
    hide(w);
    robot.waitForIdle();
    robot.close(w);
    assertThat(isShowing(w)).isFalse();
  }
}
