/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JTableCellReader;
import org.uitest4j.swing.edt.GuiQuery;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the contents of a {@code JTable} as a two-dimensional {@code String} array. This query is executed in the
 * event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JTableContentsQuery {
	@RunsInEDT
	static @Nonnull
	String[][] tableContents(final @Nonnull JTable table, final @Nonnull JTableCellReader cellReader) {
		String[][] result = execute(new GuiQuery<>() {
			@Override
			@Nonnull
			protected String[][] executeInEDT() {
				int rCount = table.getRowCount();
				int cCount = table.getColumnCount();
				String[][] contents = new String[rCount][cCount];
				for (int r = 0; r < rCount; r++) {
					for (int c = 0; c < cCount; c++) {
						contents[r][c] = cellReader.valueAt(table, r, c);
					}
				}
				return contents;
			}
		});
		return Objects.requireNonNull(result);
	}

	private JTableContentsQuery() {
	}
}
