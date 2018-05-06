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

import org.junit.jupiter.api.Test;

import static org.uitest4j.swing.test.swing.JOptionPaneLauncher.launch;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Tests for {@link JOptionPaneDriver#okButton(JOptionPane)}.
 * 
 * @author Alex Ruiz
 */
class JOptionPaneDriver_okButton_Test extends JOptionPaneDriver_TestCase {
  @Test
  void should_Find_OK_Button() {
    JOptionPane optionPane = informationMessage();
    launch(optionPane, title());
    JButton button = driver.okButton(optionPane);
    assertThatButtonHasText(button, "OptionPane.okButtonText");
  }
}
