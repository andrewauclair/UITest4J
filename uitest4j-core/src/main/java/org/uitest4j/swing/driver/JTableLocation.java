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

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.data.TableCell;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A visible location on a {@code JTable}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JTableLocation {
	/**
	 * <p>
	 * Converts the given row and column into a coordinate pair. It is assumed that the row and column indices are in the
	 * {@code JTable}'s bounds.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param table  the target {@code JTable}.
	 * @param row    the given row.
	 * @param column the given column.
	 * @return the coordinates of the given row and column.
	 */
	@RunsInCurrentThread
	@Nonnull
	public Point pointAt(@Nonnull JTable table, int row, int column) {
		Rectangle cellBounds = cellBounds(table, row, column);
		return new Point(cellBounds.x + cellBounds.width / 2, cellBounds.y + cellBounds.height / 2);
	}

	/**
	 * <p>
	 * Returns the bounds of the given cell.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param table the target {@code JTable}.
	 * @param cell  the given cell.
	 * @return the bounds of the given cell.
	 */
	@RunsInCurrentThread
	@Nonnull
	public Rectangle cellBounds(@Nonnull JTable table, @Nonnull TableCell cell) {
		return cellBounds(table, cell.row, cell.column);
	}

	/**
	 * <p>
	 * Returns the bounds of the given row and column.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param table  the target {@code JTable}.
	 * @param row    the given row.
	 * @param column the given column.
	 * @return the bounds of the given row and column.
	 */
	@RunsInCurrentThread
	@Nonnull
	public Rectangle cellBounds(JTable table, int row, int column) {
		return Objects.requireNonNull(table.getCellRect(row, column, false));
	}
}
