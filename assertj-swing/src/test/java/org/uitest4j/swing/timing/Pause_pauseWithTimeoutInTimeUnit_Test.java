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
package org.uitest4j.swing.timing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.util.StopWatch.startNewStopWatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.uitest4j.swing.test.util.StopWatch;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Pause#pause(long, java.util.concurrent.TimeUnit)}.
 * 
 * @author Alex Ruiz
 */
class Pause_pauseWithTimeoutInTimeUnit_Test {
  @Test
  void should_Pause_For_The_Given_Amount_Of_Time() {
    StopWatch watch = startNewStopWatch();
    long delay = 2000;
    Pause.pause(2, TimeUnit.SECONDS);
    watch.stop();
    assertThat(watch.ellapsedTime() >= delay).isTrue();
  }

  @Test
  void should_Throw_Error_If_Unit_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> Pause.pause(2, null));
  }
}
