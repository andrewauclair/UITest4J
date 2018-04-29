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
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WindowDriver#moveToBack(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 */
class WindowDriver_moveToBack_Test extends WindowDriver_TestCase {

  private final CountDownLatch latch = new CountDownLatch(1);

  /** Timeout is important - to fail if the window is never activated! */
  @Test
  void should_Move_Window_To_Back() throws InterruptedException {
    // TODO(alruiz): Test on Windows
    assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
      showWindow();

      TestWindow window2 = TestWindow.createAndShowNewWindow(getClass());
      registerDeactivationLatch(window2);
      // wait for idle, since some OSs may take a while
      driver.robot.waitForIdle();

      assertThat(isActive(window2)).isTrue();
      driver.moveToBack(window2);

      // see Window#toBack: no guarantees about changes to the focused and active Windows can be made.
      // we have to wait for the window to be deactivated
      latch.await();
      assertThat(isActive(window2)).isFalse();
    });
  }

  private void registerDeactivationLatch(TestWindow window2) {
    window2.addWindowListener(new WindowAdapter() {
      @Override
      public void windowDeactivated(WindowEvent e) {
        super.windowDeactivated(e);
        latch.countDown();
      }
    });
  }
}
