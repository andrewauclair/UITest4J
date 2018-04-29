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

/**
 * Tests for {@link TextAssert#isEqualOrMatches(String)}.
 * 
 * @author Alex Ruiz
 */
class TextAssert_isEqualOrMatches_Test {
  private final String NL = System.getProperty("line.separator");

  @Test
  void should_Fail_If_Actual_Is_Not_Equal_To_Expected() {
    ExpectedException.assertContainsMessage(AssertionError.class, () -> new TextAssert("hello").isEqualOrMatches("bye"), "Expecting:" + NL + " \"hello\"" + NL + "to match pattern:" + NL + " \"bye\"");
  }

  @Test
  void should_Fail_Showing_Description_If_Actual_Is_Not_Equal_To_Expected() {
    ExpectedException.assertContainsMessage(AssertionError.class, () -> new TextAssert("hello").as("A Test").isEqualOrMatches("bye"), "[A Test] " + NL + "Expecting:" + NL + " \"hello\"" + NL + "to match pattern:" + NL + " \"bye\"");
  }

  @Test
  void should_Pass_If_Actual_Is_Equal_To_Expected_But_No_Valid_Pattern() {
    new TextAssert("[He$$o").isEqualOrMatches("[He$$o");
  }

  @Test
  void should_Pass_If_Actual_Is_Equal_To_Expected() {
    new TextAssert("Hello").isEqualOrMatches("Hello");
  }

  @Test
  void should_Pass_If_Actual_Matches_Regex_Pattern() {
    new TextAssert("Hello").isEqualOrMatches("Hell.");
  }
}
