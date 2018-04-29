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

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.test.swing.JOptionPaneLauncher.pack;

import javax.swing.JOptionPane;

/**
 * Tests for {@link JOptionPaneDriver#requireOptions(JOptionPane, Object[])}.
 * 
 * @author Alex Ruiz
 */
class JOptionPaneDriver_requireOptions_Test extends JOptionPaneDriver_TestCase {
  @Test
  void should_Pass_If_Options_Are_Equal_To_Expected() {
    JOptionPane optionPane = messageWithOptions("First", "Second");
    pack(optionPane, title());
    driver.requireOptions(optionPane, array("First", "Second"));
  }

  @Test
  void should_Fail_If_Options_Are_Not_Equal_To_Expected() {
    JOptionPane optionPane = messageWithOptions("First", "Second");
    pack(optionPane, title());
    ExpectedException.assertAssertionError(() -> driver.requireOptions(optionPane, array("Third")), "options", array("[Thir]d"), array("[First", "Secon]d"));
  }
}
