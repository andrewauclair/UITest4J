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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JListDriver#requireSelection(javax.swing.JList, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_requireSelectionAsPattern_Test extends JListDriver_TestCase {
  @Test
  void should_Pass_If_Selection_Matches_Pattern() {
    selectFirstItem();
    driver.requireSelection(list, Pattern.compile("on.*"));
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Fail_If_There_Is_No_Selection() {
    clearSelection();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(list, Pattern.compile("one")), "property:'selectedIndex'", "No selection");
  }

  @Test
  void should_Fail_If_Selection_Does_Not_Match_Pattern() {
    select(1);
    ExpectedException.assertAssertionError(() -> driver.requireSelection(list, Pattern.compile("one")), "selectedIndex", "two", Pattern.compile("one"));
  }
}
