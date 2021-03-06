/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.core.ComponentLookupScope.ALL;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=20&can=2&q=" target="_blank">issue 20</a>.
 *
 * @author Alex Ruiz
 */
public class Bug20_cannotFindComponentInJPanel_Test extends RobotBasedTestCase {
	private FrameFixture frame;

	@Override
	protected void onSetUp() {
		robot.settings().componentLookupScope(ALL);
		frame = new FrameFixture(robot, MyWindow.createNew());
	}

	@Test
	public void should_Find_List() {
		frame.list("list"); // should find list
	}

	static class MyWindow extends TestWindow {
		private final JPanel panel = new JPanel();
		private final JList list = new JList();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(Bug20_cannotFindComponentInJPanel_Test.class);
			add(panel);
			panel.add(new JScrollPane(list));
			list.setName("list");
		}
	}
}
