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
 * Tests for {@link JListDriver#requireSelectedItems(javax.swing.JList, String...)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_requireSelectedItemsAsText_Test extends JListDriver_TestCase {
  @Test
  void should_Fail_If_There_Is_No_Selection() {
    clearSelection();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelectedItems(list, "one", "two"), "property:'selectedIndices'", "expected:<[[\"one\", \"two\"]]> but was:<[[]]>");
  }

  @Test
  void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
    select(2);
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelectedItems(list, "one"), "property:'selectedIndices'", "expected:<[\"[on]e\"]> but was:<[\"[thre]e\"]>");
  }

  @Test
  void should_Pass_If_Selection_Is_Equal_To_Expected() {
    select(0, 1);
    driver.requireSelectedItems(list, "two", "one");
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Pass_If_Selection_Matches_Pattern() {
    select(1, 2);
    driver.requireSelectedItems(list, "t.*");
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Pass_If_Selection_Matches_Patterns() {
    select(0, 1);
    driver.requireSelectedItems(list, "tw.*", "o.*");
    assertThatCellReaderWasCalled();
  }
}
