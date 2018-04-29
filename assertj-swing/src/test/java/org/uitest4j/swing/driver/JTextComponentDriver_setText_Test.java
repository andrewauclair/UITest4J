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

/**
 * Tests for {@link JTextComponentDriver#setText(javax.swing.text.JTextComponent, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTextComponentDriver_setText_Test extends JTextComponentDriver_TestCase {
  @Test
  void should_Set_Text() {
    showWindow();
    clearTextField();
    driver.setText(textField, "Entering text");
    requireTextInTextField("Entering text");
  }

  @Test
  void should_Throw_Error_If_JTextComponent_Is_Disabled() {
    disableTextField();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.setText(textField, "Entering text"));
  }

  @Test
  void should_Throw_Error_If_JTextComponent_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.setText(textField, "Entering text"));
  }
}
