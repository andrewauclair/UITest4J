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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TableRenderDemo;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.data.TableCell.row;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTableCellEditableQuery#isCellEditable(JTable, org.uitest4j.swing.data.TableCell)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableCellEditableQuery_isCellEditable_Test extends RobotBasedTestCase {
	private JTable table;

	private static Collection<Object[]> cells() {
		return newArrayList(new Object[][]{{0, false}, {1, false}, {2, true}, {3, true}, {4, true},});
	}

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		table = window.table;
	}

	@ParameterizedTest
	@MethodSource("cells")
	void shouldIndicateWhetherCellIsEditableOrNot(int column, boolean editable) {
		// TODO test validation of cell indices
		assertThat(isCellEditable(table, 0, column)).isEqualTo(editable);
	}

	@RunsInEDT
	private static boolean isCellEditable(final JTable table, final int row, final int column) {
		return execute(() -> JTableCellEditableQuery.isCellEditable(table, row(row).column(column)));
	}

	private static class MyWindow extends TestWindow {
		final JTable table;

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JTableCellEditableQuery_isCellEditable_Test.class);
			TableRenderDemo content = new TableRenderDemo();
			table = content.table;
			setContentPane(content);
		}
	}
}
