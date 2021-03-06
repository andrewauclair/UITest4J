/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.finder.WindowFinder.findFrame;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=108">Bug 108</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class Bug108_findFrameByNameAndType_Test extends RobotBasedTestCase {

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		robot.showWindow(window);
	}

	@Test
	void should_Not_Find_Frame_When_Using_Wrong_Name() {
		assertThrows(WaitTimedOutError.class, () -> findFrame("yourFrame").using(robot));
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(Bug108_findFrameByNameAndType_Test.class);
			setName("myFrame");
			setPreferredSize(new Dimension(160, 60));
		}
	}
}