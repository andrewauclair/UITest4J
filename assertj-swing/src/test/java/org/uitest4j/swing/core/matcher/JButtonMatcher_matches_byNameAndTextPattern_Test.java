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
package org.uitest4j.swing.core.matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JButtons.button;

import java.util.regex.Pattern;

import javax.swing.JButton;

import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JButtonMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JButtonMatcher_matches_byNameAndTextPattern_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Name_Is_Equal_To_Expected_And_Text_Matches_Pattern() {
    JButtonMatcher matcher = JButtonMatcher.withName("button").andText(Pattern.compile("Hel.*"));
    JButton button = button().withName("button").withText("Hello").createNew();
    assertThat(matcher.matches(button)).isTrue();
  }
}
