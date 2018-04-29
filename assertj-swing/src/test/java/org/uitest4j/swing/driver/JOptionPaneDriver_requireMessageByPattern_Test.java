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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

import static org.uitest4j.swing.test.swing.JOptionPaneLauncher.pack;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * Tests for {@link JOptionPaneDriver#requireMessage(JOptionPane, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 */
class JOptionPaneDriver_requireMessageByPattern_Test extends JOptionPaneDriver_TestCase {
  @Test
  void should_Pass_If_Message_Matches_Pattern() {
    JOptionPane optionPane = messageWithValue("Leia");
    pack(optionPane, title());
    driver.requireMessage(optionPane, Pattern.compile("Le.*"));
  }

  @Test
  void should_Pass_If_Non_String_Message_Is_Equal_To_Expected() {
    JOptionPane optionPane = messageWithValue(new Person("Leia"));
    pack(optionPane, title());
    driver.requireMessage(optionPane, Pattern.compile("Le.*"));
  }

  @Test
  void should_Fail_Is_Message_Does_Match_Pattern() {
    JOptionPane optionPane = messageWithValue("Palpatine");
    pack(optionPane, title());
    ExpectedException.assertAssertionError(() -> driver.requireMessage(optionPane, Pattern.compile("Anakin")), "message", "Palpatine", Pattern.compile("Anakin"));
  }
}
