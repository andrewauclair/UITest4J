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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=246" target="_blank">Bug 246</a>.
 *
 * @author Alex Ruiz
 */
public class Bug246_NpeWhenSelectingAlreadySelectedItemInJList_Test extends RobotBasedTestCase {
	private JListDriver driver;
	private JList list;

	@Override
	protected void onSetUp() {
		driver = new JListDriver(robot);
		MyWindow window = MyWindow.createNew();
		list = window.list;
		robot.showWindow(window);
	}

	@Test
	public void should_Not_Select_Item_If_Already_Selected() {
		driver.selectItem(list, "One");
		assertThat(list.getSelectedIndex()).isEqualTo(0);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JList list = new JList(array("One", "Two"));

		private MyWindow() {
			super(Bug246_NpeWhenSelectingAlreadySelectedItemInJList_Test.class);
			addComponents(list);
			list.setSelectedIndex(0);
		}
	}
}
