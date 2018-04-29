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

import java.util.regex.Pattern;

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTabbedPaneDriver#selectTab(javax.swing.JTabbedPane, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 */
class JTabbedPaneDriver_selectTabByPattern_Test extends JTabbedPaneDriver_TestCase {
  @Test
  void should_Select_Matching_Tab() {
    showWindow();
    driver.selectTab(tabbedPane, Pattern.compile("Tw.*"));
    assertThatSelectedTabIndexIs(1);
    assertThatSelectedTabIndexIs(1);
  }

  @Test
  void should_Throw_Error_If_JTabbedPane_Is_Disabled() {
    disableTabbedPane();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectTab(tabbedPane, Pattern.compile("Tw.*")));
  }

  @Test
  void should_Throw_Error_If_JTabbedPane_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectTab(tabbedPane, Pattern.compile("Tw.*")));
  }
}
