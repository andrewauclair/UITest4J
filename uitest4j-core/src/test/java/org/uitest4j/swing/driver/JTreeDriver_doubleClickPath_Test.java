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
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JTreeDriver#doubleClickPath(JTree, String)}.
 *
 * @author Alex Ruiz
 */
public class JTreeDriver_doubleClickPath_Test extends JTreeDriver_clickCell_TestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Double_Click_Cell() {
		showWindow();
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(tree);
		driver.doubleClickPath(tree, "root");
		recorder.clicked(MouseButton.LEFT_BUTTON).timesClicked(2);
		assertThat(rowAt(recorder.pointClicked())).isEqualTo(0);
	}

	@Test
	void should_Throw_Error_If_JTree_Is_Disabled() {
		disableTree();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.doubleClickPath(tree, "root"));
	}

	@Test
	void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.doubleClickPath(tree, "root"));
	}
}
