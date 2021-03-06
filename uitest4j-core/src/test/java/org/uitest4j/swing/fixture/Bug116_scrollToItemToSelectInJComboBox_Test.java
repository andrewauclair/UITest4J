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

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=116">Bug 116</a>.
 *
 * @author Yvonne Wang
 */
public class Bug116_scrollToItemToSelectInJComboBox_Test extends RobotBasedTestCase {
	private JComboBoxFixture comboBox;
	private MyWindow window;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew();
		comboBox = new JComboBoxFixture(robot, window.comboBox);
		robot.showWindow(window);
	}

	@Test
	public void should_Scroll_To_Item_Before_Selecting_It() {
		int toSelect = 99;
		comboBox.selectItem(toSelect);
		assertThat(selectedIndexOf(window.comboBox)).isEqualTo(toSelect);
	}

	@RunsInEDT
	private static int selectedIndexOf(final JComboBox comboBox) {
		return execute(comboBox::getSelectedIndex);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JComboBox comboBox = new JComboBox();

		private MyWindow() {
			super(Bug116_scrollToItemToSelectInJComboBox_Test.class);
			add(comboBox);
			int itemCount = 100;
			Object[] content = new Object[itemCount];
			for (int i = 0; i < itemCount; i++) {
				content[i] = String.valueOf(i + 1);
			}
			comboBox.setModel(new DefaultComboBoxModel(content));
		}
	}
}
