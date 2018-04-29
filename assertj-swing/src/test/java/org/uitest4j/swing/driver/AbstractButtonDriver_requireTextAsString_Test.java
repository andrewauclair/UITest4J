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

import java.util.regex.Pattern;

/**
 * Tests for {@link AbstractButtonDriver#requireText(javax.swing.AbstractButton, String)}.
 * 
 * @author Alex Ruiz
 */
class AbstractButtonDriver_requireTextAsString_Test extends AbstractButtonDriver_TestCase {
  @Test
  void should_Pass_If_Text_Is_Equal_To_Given_String() {
    driver.requireText(checkBox, "Hello");
  }

  @Test
  void should_Pass_If_Text_Matches_Regex_Pattern_In_Given_String() {
    driver.requireText(checkBox, "Hell.");
  }

  @Test
  void should_Fail_If_Text_Is_Not_Equal_To_Given_String() {
    ExpectedException.assertAssertionError(() -> driver.requireText(checkBox, "Bye"), "text", "Hello", Pattern.compile("Bye"));
  }

  @Test
  void should_Fail_If_Text_Does_Not_Match_Regex_Pattern_In_Given_String() {
    ExpectedException.assertAssertionError(() -> driver.requireText(checkBox, "Bye."), "text", "Hello", Pattern.compile("Bye."));
  }
}
