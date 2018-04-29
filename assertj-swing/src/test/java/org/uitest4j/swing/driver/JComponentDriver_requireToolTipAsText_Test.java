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
 * Tests for {@link JComponentDriver#requireToolTip(JComponent, String)}.
 * 
 * @author Alex Ruiz
 */
class JComponentDriver_requireToolTipAsText_Test extends JComponentDriver_TestCase {
  @Test
  void should_Pass_If_ToolTip_Is_Equal_To_Given_Text() {
    driver.requireToolTip(button, "A ToolTip");
  }

  @Test
  void should_Pass_If_ToolTip_Matches_Pattern_In_Given_Text() {
    driver.requireToolTip(button, "A Tool.*");
  }

  @Test
  void should_Fail_If_ToolTip_Is_Not_Equal_To_Given_Text() {
    ExpectedException.assertAssertionError(() -> driver.requireToolTip(button, "Hello"), "toolTipText", "A ToolTip", Pattern.compile("Hello"));
  }
}
