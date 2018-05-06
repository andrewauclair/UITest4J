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

import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

/**
 * Tests for {@link JTableHeaderDriver#clickColumn(javax.swing.table.JTableHeader, java.util.regex.Pattern)}.
 * 
 * @author Yvonne Wang
 */
class JTableHeaderDriver_clickColumnByPattern_Test extends JTableHeaderDriver_TestCase {
  private ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Fail_If_Matching_Column_Was_Not_Found() {
    ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> driver.clickColumn(tableHeader, Pattern.compile("hello")), "Unable to find column with name matching pattern 'hello'");
  }

  @Test
  void should_Click_Column() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tableHeader);
    driver.clickColumn(tableHeader, Pattern.compile("0.*"));
    recorder.wasClicked();
    assertThatColumnWasClicked(recorder, 0);
  }
}
