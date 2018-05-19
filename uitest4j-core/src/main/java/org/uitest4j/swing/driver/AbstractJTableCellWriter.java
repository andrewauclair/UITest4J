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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JTableCellWriter;
import org.uitest4j.swing.core.ComponentFoundCondition;
import org.uitest4j.swing.core.ComponentMatcher;
import org.uitest4j.core.api.Robot;
import org.uitest4j.swing.core.TypeMatcher;
import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.swing.exception.WaitTimedOutError;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Objects;

import static java.lang.String.valueOf;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JTableCancelCellEditingTask.cancelEditing;
import static org.uitest4j.swing.driver.JTableCellEditorQuery.cellEditorIn;
import static org.uitest4j.swing.driver.JTableCellPreconditions.checkCellIndicesInBounds;
import static org.uitest4j.swing.driver.JTableCellPreconditions.validateCellIsEditable;
import static org.uitest4j.swing.driver.JTableStopCellEditingTask.checkStateAndStopEditing;
import static org.uitest4j.swing.driver.JTableStopCellEditingTask.stopEditing;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.exception.ActionFailedException.actionFailure;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Template for implementations of {@link JTableCellWriter}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractJTableCellWriter implements JTableCellWriter {
	protected final Robot robot;
	private final JTableLocation location = new JTableLocation();

	private TableCellEditor cellEditor;

	private static final long EDITOR_LOOKUP_TIMEOUT = 5000;

	public AbstractJTableCellWriter(@Nonnull Robot robot) {
		this.robot = robot;
	}

	@RunsInEDT
	@Override
	public void cancelCellEditing(@Nonnull JTable table, int row, int column) {
		if (cellEditor == null) {
			doCancelCellEditing(table, row, column);
			return;
		}
		doCancelCellEditing();
	}

	@RunsInEDT
	private void doCancelCellEditing(@Nonnull JTable table, int row, int column) {
		cancelEditing(table, row, column);
		robot.waitForIdle();
	}

	@RunsInEDT
	private void doCancelCellEditing() {
		cancelEditing(cellEditor);
		robot.waitForIdle();
	}

	@RunsInEDT
	@Override
	public void stopCellEditing(@Nonnull JTable table, int row, int column) {
		if (cellEditor == null) {
			doStopCellEditing(table, row, column);
			return;
		}
		doStopCellEditing();
	}

	@RunsInEDT
	private void doStopCellEditing(@Nonnull JTable table, int row, int column) {
		checkStateAndStopEditing(table, row, column);
		robot.waitForIdle();
	}

	@RunsInEDT
	private void doStopCellEditing() {
		stopEditing(cellEditor);
		robot.waitForIdle();
	}

	/**
	 * Returns the editor for the given {@code JTable} cell. This method is executed in the EDT.
	 *
	 * @param table  the given {@code JTable}.
	 * @param row    the row index of the cell.
	 * @param column the column index of the cell.
	 * @return the editor for the given {@code JTable} cell.
	 */
	@RunsInEDT
	@Nullable
	protected static TableCellEditor cellEditor(final @Nonnull JTable table, final int row, final int column) {
		return execute(() -> table.getCellEditor(row, column));
	}

	/**
	 * <p>
	 * Scrolls the given {@code JTable} to the given cell.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param table    the given {@code JTable}.
	 * @param row      the row index of the cell.
	 * @param column   the column index of the cell.
	 * @param location obtains the bounds of the given cell.
	 */
	@RunsInCurrentThread
	protected static void scrollToCell(JTable table, int row, int column, JTableLocation location) {
		table.scrollRectToVisible(location.cellBounds(table, row, column));
	}

	@RunsInEDT
	@Override
	@Nullable
	public Component editorForCell(@Nonnull JTable table, int row, int column) {
		return cellEditorComponent(table, row, column);
	}

	@RunsInEDT
	@Nullable
	private static Component cellEditorComponent(final @Nonnull JTable table, final int row, final int column) {
		return execute(() -> {
			checkCellIndicesInBounds(table, row, column);
			return cellEditorIn(table, row, column);
		});
	}

	/**
	 * <p>
	 * Finds the AWT or Swing {@code Component} used as editor for the given {@code JTable}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param table         the given {@code JTable}.
	 * @param row           the row index of the cell.
	 * @param column        the column index of the cell.
	 * @param <T>           the type of component we expect as editor.
	 * @param supportedType the class of the type of component.
	 * @return the component used as editor for the given table cell.
	 * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the {@code JTable} does not have any
	 *                                   rows.
	 * @throws IllegalStateException     if the {@code JTable} is disabled.
	 * @throws IllegalStateException     if the {@code JTable} is not showing on the screen.
	 * @throws IllegalStateException     if the table cell in the given coordinates is not editable.
	 * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the {@code JTable} does not have any
	 *                                   rows.
	 * @throws ActionFailedException     if an editor for the given cell cannot be found or cannot be activated.
	 */
	@RunsInCurrentThread
	@Nonnull
	protected static <T extends Component> T editor(@Nonnull JTable table, int row, int column,
													@Nonnull Class<T> supportedType) {
		validate(table, row, column);
		Component editor = cellEditorIn(table, row, column);
		if (supportedType.isInstance(editor)) {
			return supportedType.cast(editor);
		}
		throw cannotFindOrActivateEditor(row, column);
	}

	/**
	 * Returns the location of the given table cell.
	 *
	 * @param table    the given {@code JTable}.
	 * @param row      the row index of the cell.
	 * @param column   the column index of the cell.
	 * @param location knows how to get the location of a table cell.
	 * @return the location of the given table cell.
	 * @throws IllegalStateException     if the {@code JTable} is disabled.
	 * @throws IllegalStateException     if the {@code JTable} is not showing on the screen.
	 * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the {@code JTable} does not have any
	 *                                   rows.
	 * @throws IllegalStateException     if the table cell in the given coordinates is not editable.
	 */
	@RunsInEDT
	@Nonnull
	protected static Point cellLocation(final @Nonnull JTable table, final int row, final int column,
										final @Nonnull JTableLocation location) {
		Point result = execute(() -> {
			validate(table, row, column);
			scrollToCell(table, row, column, location);
			return location.pointAt(table, row, column);
		});
		return Objects.requireNonNull(result);
	}

	/**
	 * <p>
	 * Validates that:
	 * </p>
	 * <ol>
	 * <li>the given {@code JTable} is enabled and showing on the screen</li>
	 * <li>the row and column indices are correct (not out of bounds)</li>
	 * <li>the table cell at the given indices is editable</li>
	 * </ol>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param table  the given {@code JTable}.
	 * @param row    the row index of the cell.
	 * @param column the column index of the cell.
	 * @throws IllegalStateException     if the {@code JTable} is disabled.
	 * @throws IllegalStateException     if the {@code JTable} is not showing on the screen.
	 * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the {@code JTable} does not have any
	 *                                   rows.
	 * @throws IllegalStateException     if the table cell in the given coordinates is not editable.
	 */
	@RunsInCurrentThread
	protected static void validate(final @Nonnull JTable table, final int row, final int column) {
		checkCellIndicesInBounds(table, row, column);
		checkEnabledAndShowing(table);
		validateCellIsEditable(table, row, column);
	}

	/**
	 * Waits until the editor of the given table cell is showing on the screen. Component lookup is performed by type.
	 *
	 * @param table         the given {@code JTable}.
	 * @param row           the row index of the cell.
	 * @param column        the column index of the cell.
	 * @param <T>           the type of component we expect as editor.
	 * @param supportedType the class of the type of component.
	 * @return the editor of the given table cell once it is showing on the screen.
	 * @throws ActionFailedException if an editor for the given cell cannot be found or cannot be activated.
	 */
	@RunsInEDT
	protected final @Nullable
	<T extends Component> T waitForEditorActivation(@Nonnull JTable table, int row, int column,
													@Nonnull Class<T> supportedType) {
		return waitForEditorActivation(new TypeMatcher(supportedType, true), table, row, column, supportedType);
	}

	/**
	 * Waits until the editor of the given table cell is showing on the screen.
	 *
	 * @param matcher       the condition that the cell editor to look for needs to satisfy.
	 * @param table         the given {@code JTable}.
	 * @param row           the row index of the cell.
	 * @param column        the column index of the cell.
	 * @param <T>           the type of component we expect as editor.
	 * @param supportedType the class of the type of component.
	 * @return the editor of the given table cell once it is showing on the screen.
	 * @throws ActionFailedException if an editor for the given cell cannot be found or cannot be activated.
	 */
	@RunsInEDT
	protected final @Nullable
	<T extends Component> T waitForEditorActivation(@Nonnull ComponentMatcher matcher,
													@Nonnull JTable table, int row, int column,
													@Nonnull Class<T> supportedType) {
		ComponentFoundCondition condition = new ComponentFoundCondition("", robot.finder(), matcher, table);
		try {
			pause(condition, EDITOR_LOOKUP_TIMEOUT);
		}
		catch (WaitTimedOutError e) {
			throw cannotFindOrActivateEditor(row, column);
		}
		return supportedType.cast(condition.found());
	}

	/**
	 * Throws a {@link ActionFailedException} if this {@link JTableCellWriter} could not find or activate the cell editor
	 * of the supported type.
	 *
	 * @param row    the row index of the cell.
	 * @param column the column index of the cell.
	 * @return the thrown exception.
	 */
	@Nonnull
	protected static ActionFailedException cannotFindOrActivateEditor(int row, int column) {
		String msg = "Unable to find or activate editor for cell [" + valueOf(row) + "," + valueOf(column) + "]";
		throw actionFailure(msg);
	}

	/**
	 * @return the cell editor being currently used, or {@code null} if no table cell is being currently edited.
	 */
	protected final @Nullable
	TableCellEditor cellEditor() {
		return cellEditor;
	}

	/**
	 * Sets the cell editor being currently used.
	 *
	 * @param newCellEditor the cell editor being currently used.
	 */
	protected final void cellEditor(@Nullable TableCellEditor newCellEditor) {
		cellEditor = newCellEditor;
	}

	protected final @Nonnull
	JTableLocation location() {
		return location;
	}
}
