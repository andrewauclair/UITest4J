/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.data;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestTable;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for implementations of {@link TableCellFinder}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class TableCellFinder_TestCase extends RobotBasedTestCase {
	MyWindow window;
	TestTable table;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew(getClass());
		table = window.table;
		extraSetUp();
	}

	void extraSetUp() {
	}

	static class MyWindow extends TestWindow {
		private static final Dimension TABLE_SIZE = new Dimension(400, 100);

		static final int COLUMN_COUNT = 6;
		static final int ROW_COUNT = 10;

		final TestTable table = new TestTable(ROW_COUNT, COLUMN_COUNT);

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			addComponents(decorate(table));
		}

		private static Component decorate(JTable table) {
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(TABLE_SIZE);
			return scrollPane;
		}
	}
}
