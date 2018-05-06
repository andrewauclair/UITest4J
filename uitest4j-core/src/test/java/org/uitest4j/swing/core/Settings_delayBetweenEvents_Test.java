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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.Robot;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link Settings#delayBetweenEvents(int)} and {@link Settings#delayBetweenEvents()}.
 * 
 * @author Alex Ruiz
 */
class Settings_delayBetweenEvents_Test {
  private Settings settings;
  private java.awt.Robot robot;

  private static Collection<Object[]> autoDelays() {
    return newArrayList(new Object[][] { { 100 }, { 200 }, { 68 } });
  }

  @BeforeEach
  void setUp() throws Exception {
    settings = new Settings();
    robot = new Robot();
  }

  @ParameterizedTest
  @MethodSource("autoDelays")
  void shouldUpdateAndReturnDelayBetweenEvents(int delay) {
    settings.attachTo(robot);
    settings.delayBetweenEvents(delay);
    assertThat(robot.getAutoDelay()).isEqualTo(settings.delayBetweenEvents());
  }
}
