/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import java.util.regex.Pattern;

import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;

/**
 * Tests for {@link JTableHeaderDriver#clickColumn(javax.swing.table.JTableHeader, String)}.
 *
 * @author Yvonne Wang
 */
public class JTableHeaderDriver_clickColumnByPatternUsingButtonAndTimes_Test extends JTableHeaderDriver_TestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Click_Column() {
		showWindow();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(tableHeader);
		driver.clickColumn(tableHeader, Pattern.compile("0.*"), LEFT_BUTTON, 3);
		recorder.wasClickedWith(LEFT_BUTTON).timesClicked(3);
		assertThatColumnWasClicked(recorder, 0);
	}
}
