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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;

import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JListDriver#indexOf(javax.swing.JList, java.util.regex.Pattern)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_indexOfValueAsPattern_Test extends JListDriver_TestCase {
  @Test
  void should_Return_Index_Of_Item_Matching_Pattern() {
    int index = driver.indexOf(list, Pattern.compile("thr.*"));
    assertThat(index).isEqualTo(2);
    assertThatCellReaderWasCalled();
  }

  @Test
  void should_Throw_Error_If_Item_Matching_Given_Value_Was_Not_Found() {
    ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> driver.indexOf(list, Pattern.compile("fou.*")),
        "Unable to find item matching the pattern 'fou.*' among the JList contents [\"one\", \"two\", \"three\"]");
  }
}
