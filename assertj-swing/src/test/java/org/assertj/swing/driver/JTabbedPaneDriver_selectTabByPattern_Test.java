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
package org.assertj.swing.driver;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTabbedPaneDriver#selectTab(javax.swing.JTabbedPane, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 */
public class JTabbedPaneDriver_selectTabByPattern_Test extends JTabbedPaneDriver_TestCase {
  @Test
  public void should_Select_Matching_Tab() {
    showWindow();
    driver.selectTab(tabbedPane, Pattern.compile("Tw.*"));
    assertThatSelectedTabIndexIs(1);
    assertThatSelectedTabIndexIs(1);
  }

  @Test
  public void should_Throw_Error_If_JTabbedPane_Is_Disabled() {
    disableTabbedPane();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectTab(tabbedPane, Pattern.compile("Tw.*"));
  }

  @Test
  public void should_Throw_Error_If_JTabbedPane_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectTab(tabbedPane, Pattern.compile("Tw.*"));
  }
}
