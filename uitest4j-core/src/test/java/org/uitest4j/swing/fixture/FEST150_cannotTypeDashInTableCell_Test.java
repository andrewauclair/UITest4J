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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.data.TableCell.row;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-150" target="_blank">FEST-150</a>.
 *
 * @author Alex Ruiz
 */
public class FEST150_cannotTypeDashInTableCell_Test extends SwingRobotBasedTestCase {
	private JTableFixture table;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		robot.showWindow(window);
		table = new JTableFixture(robot, window.table);
	}

	@Test
	void should_Enter_Dash_In_Cell() {
		table.cell(row(0).column(0)).enterValue("-");
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTable table = new JTable(6, 8);

		private MyWindow() {
			super(FEST150_cannotTypeDashInTableCell_Test.class);
			addComponents(table);
		}
	}
}
