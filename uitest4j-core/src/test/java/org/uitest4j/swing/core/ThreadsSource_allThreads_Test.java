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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ThreadsSource#allThreads}.
 * 
 * @author Alex Ruiz
 */
public class ThreadsSource_allThreads_Test {
  private ThreadsSource source;

  @BeforeEach
  void setUp() {
    source = new ThreadsSource();
  }

  @Test
  void shouldReturnAllActiveThreads() {
    Thread[] all = new Thread[Thread.activeCount()];
    Thread.enumerate(all);
    assertThat(source.allThreads()).isEqualTo(all);
  }
}
