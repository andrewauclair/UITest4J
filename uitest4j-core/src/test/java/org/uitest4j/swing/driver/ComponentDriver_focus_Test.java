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

import static org.uitest4j.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.uitest4j.swing.timing.Pause.pause;

import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.timing.Condition;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ComponentDriver#focus(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_focus_Test extends ComponentDriver_TestCase {
  @Test
  void should_Give_Focus_To_Component() {
    showWindow();
    driver.focus(window.button);
    pause(new Condition("Component has focus") {
      @Override
      public boolean test() {
        return hasFocus(window.button);
      }
    });
  }

  @Test
  void should_Throw_Error_If_Component_Is_Disabled() {
    disableButton();
    try {
      ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.focus(window.button));
    } finally {
      assertThatButtonDoesNotHaveFocus();
    }
  }

  @Test
  void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    try {
      ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.focus(window.button));
    } finally {
      assertThatButtonDoesNotHaveFocus();
    }
  }
}
