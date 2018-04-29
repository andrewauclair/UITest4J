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
package org.assertj.swing.junit.runner;

import org.uitest4j.swing.annotation.GUITest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Understands a JUnit test to be used to manually test <code>{@link GUITestRunner}</code>.
 * 
 * @author Alex Ruiz
 */
@ExtendWith(GUITestRunner.class)
class TestWithGUITestMethods {

  @GUITest
  @Test
  void successfulGUITest() {
  }

  @Disabled("enable for manual testing")
  @GUITest
  @Test
  void failedGUITest() {
    throw new RuntimeException("Failed on purpose");
  }

  @Test
  void successfulNonGUITest() {
  }

  @Disabled("enable for manual testing")
  @Test
  void failedNonGUITest() {
    throw new RuntimeException("Failed on purpose");
  }
}
