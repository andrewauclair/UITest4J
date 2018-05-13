/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.builder;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JTable}s.
 *
 * @author Alex Ruiz
 */
public final class JTables {
	private JTables() {
	}

	public static JTableFactory table() {
		return new JTableFactory();
	}

	public static class JTableFactory {
		int columnCount;
		String name;
		int rowCount;
		int selectionMode;

		public JTableFactory withColumnCount(int newColumnCount) {
			columnCount = newColumnCount;
			return this;
		}

		public JTableFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JTableFactory withRowCount(int newRowCount) {
			rowCount = newRowCount;
			return this;
		}

		public JTableFactory withSelectionMode(int newSelectionMode) {
			selectionMode = newSelectionMode;
			return this;
		}

		@RunsInEDT
		public JTable createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JTable executeInEDT() {
					JTable table = new JTable(rowCount, columnCount);
					table.setName(name);
					table.setSelectionMode(selectionMode);
					return table;
				}
			});
		}
	}
}