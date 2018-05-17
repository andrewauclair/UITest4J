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
package org.uitest4j.swing.data;

import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JTableCellReader;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Objects;

import static java.lang.String.valueOf;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.exception.ActionFailedException.actionFailure;
import static org.uitest4j.swing.query.JTableColumnByIdentifierQuery.columnIndexByIdentifier;

/**
 * <p>
 * Looks up a cell in a {@code JTable} by column identifier.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * // import static org.uitest4j.swing.data.TableCellByColumnId.row;
 * {@link org.uitest4j.swing.fixture.JTableCellFixture JTableCellFixture} cell = dialog.table("records").cell({@link TableCellByColumnId#row(int) row}(3).columnId("firstColumn"));
 * </pre>
 *
 * @author Alex Ruiz
 */
public class TableCellByColumnId implements TableCellFinder {
	public final int row;
	public final Object columnId;

	/**
	 * Starting point for the creation of a {@link TableCellByColumnId}.
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>
	 * // import static org.uitest4j.swing.data.TableCellByColumnId.row;
	 * TableCellByColumnId cell = row(5).columnId(&quot;hobbyColumn&quot;);
	 * </pre>
	 *
	 * @param row the row index of the table cell to find.
	 * @return the created builder.
	 */
	public static TableCellBuilder row(int row) {
		return new TableCellBuilder(row);
	}

	/**
	 * Factory of {@link TableCellByColumnId}s.
	 *
	 * @author Alex Ruiz
	 */
	public static class TableCellBuilder {
		private final int row;

		TableCellBuilder(int row) {
			this.row = row;
		}

		/**
		 * Creates a new table cell finder using the row index specified in {@link TableCellByColumnId#row(int)} and the
		 * column id specified as the argument in this method.
		 *
		 * @param columnId the name of the column in the table cell to find.
		 * @return the created finder.
		 */
		public TableCellByColumnId columnId(@Nonnull Object columnId) {
			return new TableCellByColumnId(row, columnId);
		}
	}

	protected TableCellByColumnId(int row, @Nonnull Object columnId) {
		this.row = row;
		this.columnId = columnId;
	}

	/**
	 * Finds a cell in the given {@code JTable} that has a matching row index and column id.
	 *
	 * @param table      the target {@code JTable}.
	 * @param cellReader knows how to read the contents of a cell in a {@code JTable}.
	 * @return the cell found, if any.
	 * @throws ActionFailedException if a matching cell could not be found.
	 */
	@RunsInEDT
	@Override
	@Nonnull
	public TableCell findCell(@Nonnull JTable table, @Nonnull JTableCellReader cellReader) {
		return findCell(table, row, columnId);
	}

	@RunsInEDT
	@Nonnull
	private static TableCell findCell(final @Nonnull JTable table, final int row, final @Nonnull Object columnId) {
		TableCell result = execute(() -> {
			int column = columnIndexByIdentifier(table, columnId);
			if (column == -1) {
				failColumnIndexNotFound(columnId);
			}
			table.convertColumnIndexToView(table.getColumn(columnId).getModelIndex());
			return new TableCell(row, column);
		});
		return Objects.requireNonNull(result);
	}

	private static void failColumnIndexNotFound(Object columnId) {
		throw actionFailure("Unable to find a column with id " + '"' + columnId + '"');
	}

	@Override
	public String toString() {
		return getClass().getName() + "[row=" + valueOf(row) + ", columnId='" + columnId + "']";
	}
}