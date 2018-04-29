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

import org.assertj.core.util.Arrays;
import org.assertj.swing.test.recorder.KeyRecorder;
import org.uitest4j.swing.timing.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.concurrent.Executors;

import static java.awt.event.KeyEvent.*;
import static org.uitest4j.swing.timing.Pause.pause;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Tests for issue with slow waitForIdle with a custom event queue
 */
class CustomEventQueue_Test extends BasicRobot_TestCase {
  private CustomEventQueue newEventQueue = new CustomEventQueue();

  @Override
  void beforeShowingWindow() {
    robot().settings().simpleWaitForIdle(true);
    Toolkit.getDefaultToolkit().getSystemEventQueue().push(newEventQueue);
  }

  @AfterEach
  void cleanup() {
    newEventQueue.remove();
  }

  @Test
  void type_keys_before_timeout() {
    giveFocusToTextField();
    final KeyRecorder recorder = KeyRecorder.attachTo(window().textField());
    Executors.newSingleThreadExecutor().execute(() -> robot().pressAndReleaseKeys(VK_A, VK_B, VK_C));
    pause(new Condition("until all keys are typed") {
      @Override
      public boolean test() {
        Integer[] expectedKeys = Arrays.array(VK_A, VK_B, VK_C);
        return recorder.keysWerePressed(expectedKeys) && recorder.keysWereReleased(expectedKeys);
      }
    }, timeout(1000));
  }

  private class CustomEventQueue extends EventQueue {
    protected void dispatchEvent(final AWTEvent event) {
      super.dispatchEvent(event);
    }

    public void remove() {
      pop();
    }
  }
}