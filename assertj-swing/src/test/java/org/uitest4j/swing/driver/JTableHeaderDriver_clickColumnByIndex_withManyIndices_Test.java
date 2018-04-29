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

import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link JTableHeaderDriver#clickColumn(javax.swing.table.JTableHeader, int)}.
 * 
 * @author Yvonne Wang
 */
class JTableHeaderDriver_clickColumnByIndex_withManyIndices_Test extends JTableHeaderDriver_TestCase {
  private ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private static Collection<Object[]> indices() {
    return newArrayList(columnIndices());
  }

  @ParameterizedTest
  @MethodSource("indices")
  void should_Click_Column(int index) {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tableHeader);
    driver.clickColumn(tableHeader, index);
    recorder.wasClicked();
    assertThatColumnWasClicked(recorder, index);
  }
}
