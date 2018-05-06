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

import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.awt.AWT.centerOf;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Base test case for implementations of {@link InputEventGenerator#pressMouse(Point, int)} and
 * {@link InputEventGenerator#releaseMouse(int)}.
 * 
 * @author Alex Ruiz
 */
public class InputEventGenerator_pressMouse_TestCase extends InputEventGenerator_TestCase {
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Nonnull
  private static Collection<Object[]> mouseButtons() {
    return newArrayList(MouseButtonProvider.mouseButtons());
  }

  @ParameterizedTest
  @MethodSource("mouseButtons")
  void should_Press_Mouse_Button_At_Given_Point_And_Release_Mouse_Button(@Nonnull MouseButton button) {
    Point center = centerOf(window);
    eventGenerator.moveMouse(window, center.x, center.y); // indirectly testing mouseMove :)
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window);
    eventGenerator.pressMouse(button.mask);
    eventGenerator.releaseMouse(button.mask);
    pause(DELAY);
    assertThat(recorder.clicked(button));
  }
}
