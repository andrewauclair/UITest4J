/**
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
package org.assertj.swing.fixture;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;

import java.awt.Component;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;

import org.assertj.core.util.VisibleForTesting;
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.core.MouseClickInfo;
import org.uitest4j.swing.data.TableCell;
import org.uitest4j.swing.driver.JTableDriver;

/**
 * <p>
 * Supports functional testing of single cells in {@code JTable}s.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * // import static org.uitest4j.swing.data.TableCell.row;
 * {@link JTableCellFixture} cell = dialog.{@link JTableFixture table}("records").cell({@link TableCell#row(int) row}(3).column(0));
 * cell.select().showPopupMenu();
 * </pre>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Christian Rösch
 *
 * @see TableCell
 */
public class JTableCellFixture implements ItemFixture<JTableCellFixture> {
  private final JTableFixture table;
  private final TableCell cell;
  private final JTable target;
  private final JTableDriver driver;

  /**
   * Creates a new {@link JTableCellFixture}.
   *
   * @param table handles the {@code JTable} containing the cell in this fixture.
   * @param cell row and column indices of the table cell to be managed by this fixture.
   * @throws NullPointerException if {@code table} is {@code null}.
   * @throws NullPointerException if {@code cell} is {@code null}.
   */
  protected JTableCellFixture(@Nonnull JTableFixture table, @Nonnull TableCell cell) {
    this(table, cell, table.target(), table.driver());
  }

  @VisibleForTesting
  JTableCellFixture(@Nonnull JTableFixture table, @Nonnull TableCell cell, @Nonnull JTable target,
                    @Nonnull JTableDriver driver) {
    this.table = checkNotNull(table);
    this.cell = checkNotNull(cell);
    this.target = target;
    this.driver = driver;
  }

  /**
   * Simulates a user selecting this fixture's table cell.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Override
  @Nonnull public JTableCellFixture select() {
    table.selectCell(cell());
    return this;
  }

  /**
   * Simulates a user unselecting this fixture's table cell.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Nonnull public JTableCellFixture unselect() {
    table.unselectCell(cell());
    return this;
  }

  /**
   * Simulates a user clicking this fixture's table cell.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Override
  @Nonnull public JTableCellFixture click() {
    table.click(cell(), LEFT_BUTTON);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's table cell.
   *
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Override
  @Nonnull public JTableCellFixture click(@Nonnull MouseClickInfo mouseClickInfo) {
    table.click(cell(), mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's table cell.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Override
  @Nonnull public JTableCellFixture doubleClick() {
    table.click(cell(), LEFT_BUTTON, 2);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's table cell.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Override
  @Nonnull public JTableCellFixture rightClick() {
    return click(RIGHT_BUTTON);
  }

  /**
   * Simulates a user clicking a cell in this fixture's table cell once, using the specified mouse button.
   *
   * @param button the mouse button to use.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseButton} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   */
  @Override
  @Nonnull public JTableCellFixture click(@Nonnull MouseButton button) {
    table.click(cell(), button);
    return this;
  }

  /**
   * Starts editing this fixture's table cell. This method should be called <strong>before</strong> manipulating the
   * {@code Component} returned by {@link #editor()}.
   * <p>
   * This method uses the {@link org.uitest4j.swing.cell.JTableCellWriter} from the {@link JTableFixture} that created
   * this fixture.
   * </p>
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if this cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws org.assertj.swing.exception.ActionFailedException if this writer is unable to handle the underlying cell
   *           editor.
   * @see JTableFixture#replaceCellWriter(org.uitest4j.swing.cell.JTableCellWriter)
   * @see org.uitest4j.swing.cell.JTableCellWriter
   * @see #editor()
   */
  @Nonnull public JTableCellFixture startEditing() {
    driver.startCellEditing(target, cell());
    return this;
  }

  /**
   * Stops editing this fixture's table cell. This method should be called <strong>after</strong> manipulating the
   * {@code Component} returned by {@link #editor()}.
   * <p>
   * This method uses the {@link org.uitest4j.swing.cell.JTableCellWriter} from the {@link JTableFixture} that created
   * this fixture.
   * </p>
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if this cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws org.assertj.swing.exception.ActionFailedException if this writer is unable to handle the underlying cell
   *           editor.
   * @see JTableFixture#replaceCellWriter(org.uitest4j.swing.cell.JTableCellWriter)
   * @see org.uitest4j.swing.cell.JTableCellWriter
   * @see #editor()
   */
  @Nonnull public JTableCellFixture stopEditing() {
    driver.stopCellEditing(target, cell());
    return this;
  }

  /**
   * <p>
   * Cancels editing this fixture's table cell. This method should be called <strong>after</strong> manipulating the
   * {@code Component} returned by {@link #editor()}.
   * </p>
   *
   * <pre>
   * TableCellFixture cell = table.cell(row(6).column(8));
   * Component editor = cell.editor();
   * // assume editor is a JTextField
   * JTextComponentFixture editorFixture = new JTextComponentFixture(robot, (JTextField) editor);
   * cell.{@link #startEditing()};
   * editorFixture.enterText(&quot;Hello&quot;);
   * // discard any entered value
   * cell.cancelEditing();
   * </pre>
   *
   * <p>
   * This method uses the {@link org.uitest4j.swing.cell.JTableCellWriter} from the {@link JTableFixture} that created
   * this fixture.
   * </p>
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if this cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws org.assertj.swing.exception.ActionFailedException if this writer is unable to handle the underlying cell
   *           editor.
   * @see JTableFixture#replaceCellWriter(org.uitest4j.swing.cell.JTableCellWriter)
   * @see org.uitest4j.swing.cell.JTableCellWriter
   * @see #editor()
   */
  @Nonnull public JTableCellFixture cancelEditing() {
    driver.cancelCellEditing(target, cell());
    return this;
  }

  /**
   * Returns the editor of this fixture's table cell. To manipulate the editor (e.g. wrapping it with a
   * {@code ComponentFixture}), the method {@link #startEditing()} should be called first. To apply any changes back to
   * the table cell(), the method {@link #stopEditing()} should be called. This method uses the
   * {@link org.uitest4j.swing.cell.JTableCellWriter} from the {@link JTableFixture} that created this fixture.
   * <p>
   * Example:
   * </p>
   *
   * <pre>
   * TableCellFixture cell = table.cell(row(6).column(8));
   * Component editor = cell.editor();
   * // assume editor is a JTextField
   * JTextComponentFixture editorFixture = new JTextComponentFixture(robot, (JTextField) editor);
   * cell.{@link #startEditing()};
   * editorFixture.enterText(&quot;Hello&quot;);
   * cell.{@link #stopEditing()};
   * </pre>
   *
   * @return the editor of this fixture's table cell.
   * @see JTableFixture#replaceCellWriter(org.uitest4j.swing.cell.JTableCellWriter)
   * @see org.uitest4j.swing.cell.JTableCellWriter
   */
  public Component editor() {
    return driver.cellEditor(target, cell());
  }

  /**
   * Enters the given value to this fixture's table cell. This method starts cell edition, enters the given value and
   * stops cell edition. To change the value of a cell(), only a call to this method is necessary. If you need more
   * flexibility, you can retrieve the cell editor with {@link #editor()}.
   * <p>
   * This method uses the {@link org.uitest4j.swing.cell.JTableCellWriter} from the {@link JTableFixture} that created
   * this fixture.
   * </p>
   *
   * @param value the value to enter in the cell.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTable} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTable} is not showing on the screen.
   * @throws IllegalStateException if this cell is not editable.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws org.assertj.swing.exception.ActionFailedException if this driver's {@code JTableCellValueReader} is unable
   *           to enter the given value.
   * @see JTableFixture#replaceCellWriter(org.uitest4j.swing.cell.JTableCellWriter)
   * @see org.uitest4j.swing.cell.JTableCellWriter
   */
  @Nonnull public JTableCellFixture enterValue(@Nonnull String value) {
    driver.enterValueInCell(target, cell(), value);
    return this;
  }

  /**
   * Asserts that the value of this fixture's table cell matches the given value.
   *
   * @param value the expected value of this fixture's table cell. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's table cell does not match the expected one.
   */
  @Nonnull public JTableCellFixture requireValue(@Nullable String value) {
    table.requireCellValue(cell(), value);
    return this;
  }

  /**
   * Asserts that the value of this fixture's table cell matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the value of this fixture's table cell does not match the expected the given regular
   *           expression pattern.
   */
  @Nonnull public JTableCellFixture requireValue(@Nonnull Pattern pattern) {
    table.requireCellValue(cell(), pattern);
    return this;
  }

  /**
   * Returns a fixture that verifies the font of this fixture's table cell. This method uses the
   * {@link org.uitest4j.swing.cell.JTableCellReader} from the {@link JTableFixture} that created this fixture.
   *
   * @return a fixture that verifies the font of this fixture's table cell.
   * @see JTableFixture#replaceCellReader(org.uitest4j.swing.cell.JTableCellReader)
   * @see org.uitest4j.swing.cell.JTableCellReader
   */
  public FontFixture font() {
    return table.fontAt(cell());
  }

  /**
   * Returns a fixture that verifies the background color of this fixture's table cell. This method uses the
   * {@link org.uitest4j.swing.cell.JTableCellReader} from the {@link JTableFixture} that created this fixture.
   *
   * @return a fixture that verifies the background color of this fixture's table cell.
   * @see JTableFixture#replaceCellReader(org.uitest4j.swing.cell.JTableCellReader)
   * @see org.uitest4j.swing.cell.JTableCellReader
   */
  @Nonnull public ColorFixture background() {
    return table.backgroundAt(cell());
  }

  /**
   * Returns a fixture that verifies the foreground color of this fixture's table cell. This method uses the
   * {@link org.uitest4j.swing.cell.JTableCellReader} from the {@link JTableFixture} that created this fixture.
   *
   * @return a fixture that verifies the foreground color of this fixture's table cell.
   * @see JTableFixture#replaceCellReader(org.uitest4j.swing.cell.JTableCellReader)
   * @see org.uitest4j.swing.cell.JTableCellReader
   */
  @Nonnull public ColorFixture foreground() {
    return table.foregroundAt(cell());
  }

  /**
   * Returns the {@code String} representation of the value of this fixture's table cell. This method uses the
   * {@link org.uitest4j.swing.cell.JTableCellReader} from the {@link JTableFixture} that created this fixture.
   *
   * @return the {@code String} representation of the value of this fixture's table cell.
   * @see JTableFixture#replaceCellReader(org.uitest4j.swing.cell.JTableCellReader)
   * @see org.uitest4j.swing.cell.JTableCellReader
   */
  @Override
  @Nullable public String value() {
    return table.valueAt(cell());
  }

  /**
   * Simulates a user dragging this fixture's table cell.
   *
   * @return this fixture.
   */
  @Override
  @Nonnull public JTableCellFixture drag() {
    table.drag(cell());
    return this;
  }

  /**
   * Simulates a user dropping into this fixture's table cell.
   *
   * @return this fixture.
   */
  @Override
  @Nonnull public JTableCellFixture drop() {
    table.drop(cell());
    return this;
  }

  /**
   * Shows a pop-up menu using this fixture's table cell as the invoker of the pop-up menu.
   *
   * @return a fixture that manages the displayed pop-up menu.
   * @throws org.assertj.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  @Nonnull public JPopupMenuFixture showPopupMenu() {
    return table.showPopupMenuAt(cell());
  }

  /**
   * Asserts that this fixture's table cell is editable.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's table cell is not editable.
   */
  @Nonnull public JTableCellFixture requireEditable() {
    table.requireEditable(cell());
    return this;
  }

  /**
   * Asserts that this fixture's table cell is not editable.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's table cell is editable.
   */
  @Nonnull public JTableCellFixture requireNotEditable() {
    table.requireNotEditable(cell());
    return this;
  }

  /**
   * @return the row index of this fixture's table cell.
   */
  public int row() {
    return cell.row;
  }

  /**
   * @return the column index of this fixture's table cell.
   */
  public int column() {
    return cell.column;
  }

  @VisibleForTesting
  @Nonnull
  JTableFixture tableFixture() {
    return table;
  }

  @VisibleForTesting
  @Nonnull
  TableCell cell() {
    return cell;
  }
}
