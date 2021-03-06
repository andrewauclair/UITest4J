/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JTableCellReader;
import org.uitest4j.swing.data.TableCell;
import org.uitest4j.swing.util.TextMatcher;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Objects;

import static org.uitest4j.swing.data.TableCell.row;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.exception.ActionFailedException.actionFailure;

/**
 * Returns the first cell in a {@code JTable} whose value matches the given one. This query is executed in the event
 * dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JTableMatchingCellQuery {
	@RunsInEDT
	static @Nonnull
	TableCell cellWithValue(final @Nonnull JTable table, final @Nonnull TextMatcher matcher,
							final @Nonnull JTableCellReader cellReader) {
		TableCell result = execute(() -> findMatchingCell(table, matcher, cellReader));
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	@Nonnull
	private static TableCell findMatchingCell(@Nonnull JTable table, @Nonnull TextMatcher matcher,
											  @Nonnull JTableCellReader cellReader) {
		int rCount = table.getRowCount();
		int cCount = table.getColumnCount();
		for (int r = 0; r < rCount; r++) {
			for (int c = 0; c < cCount; c++) {
				if (cellHasValue(table, r, c, matcher, cellReader)) {
					return row(r).column(c);
				}
			}
		}
		String msg = String.format("Unable to find cell matching %s %s", matcher.description(), matcher.formattedValues());
		throw actionFailure(msg);
	}

	@RunsInCurrentThread
	private static boolean cellHasValue(@Nonnull JTable table, int row, int column, @Nonnull TextMatcher matcher,
										@Nonnull JTableCellReader cellReader) {
		return matcher.isMatching(cellReader.valueAt(table, row, column));
	}

	private JTableMatchingCellQuery() {
	}
}
