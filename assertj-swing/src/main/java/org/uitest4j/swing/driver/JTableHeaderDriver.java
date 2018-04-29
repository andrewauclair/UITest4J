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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.util.PatternTextMatcher;
import org.uitest4j.swing.util.StringTextMatcher;
import org.uitest4j.swing.util.TextMatcher;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.regex.Pattern;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * <p>
 * Supports functional testing of {@code JTableHeader}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JTableHeaderDriver extends JComponentDriver {
  private final JTableHeaderLocation location = new JTableHeaderLocation();

  /**
   * Creates a new {@link JTableHeaderDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JTableHeaderDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Clicks the column under the given index.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnIndex the given index.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  @RunsInEDT
  public void clickColumn(@Nonnull JTableHeader tableHeader, int columnIndex) {
    clickColumn(tableHeader, columnIndex, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the column under the given index using the given mouse button the given number of times.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnIndex the given index.
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  @RunsInEDT
  public void clickColumn(@Nonnull JTableHeader tableHeader, int columnIndex, @Nonnull MouseButton button, int times) {
    Point p = pointAtIndex(tableHeader, columnIndex, location);
    robot.click(tableHeader, p, button, times);
    pause(300); // needs more time when sorting a column (JDK 1.6)
  }

  /**
   * Clicks the column which name matches the given value.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnName the column name to match. It can be a regular expression.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws org.uitest4j.swing.exception.LocationUnavailableException if a column with a matching name cannot be found.
   */
  @RunsInEDT
  public void clickColumn(@Nonnull JTableHeader tableHeader, @Nullable String columnName) {
    clickColumn(tableHeader, columnName, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the column which name matches the given regular expression pattern.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnNamePattern the the regular expression pattern to match.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws org.uitest4j.swing.exception.LocationUnavailableException if a column with a matching name cannot be found.
   */
  @RunsInEDT
  public void clickColumn(@Nonnull JTableHeader tableHeader, @Nonnull Pattern columnNamePattern) {
    clickColumn(tableHeader, columnNamePattern, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the column which name matches the given one using the given mouse button the given number of times.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnName the column name to match. It can be a regular expression.
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws org.uitest4j.swing.exception.LocationUnavailableException if a column with a matching name cannot be found.
   */
  @RunsInEDT
  public void clickColumn(@Nonnull JTableHeader tableHeader, @Nullable String columnName, @Nonnull MouseButton button,
                          int times) {
    clickColumn(tableHeader, new StringTextMatcher(columnName), button, times);
  }

  /**
   * Clicks the column which name matches the given regular expression pattern using the given mouse button the given
   * number of times.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnNamePattern the regular expression pattern to match.
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws org.uitest4j.swing.exception.LocationUnavailableException if a column with a matching name cannot be found.
   */
  @RunsInEDT
  public void clickColumn(@Nonnull JTableHeader tableHeader, @Nonnull Pattern columnNamePattern,
                          @Nonnull MouseButton button, int times) {
    clickColumn(tableHeader, new PatternTextMatcher(columnNamePattern), button, times);
  }

  @RunsInEDT
  private void clickColumn(@Nonnull JTableHeader tableHeader, @Nonnull TextMatcher matcher,
                           @Nonnull MouseButton button, int times) {
    Point p = pointAtName(tableHeader, matcher, location());
    robot.click(tableHeader, p, button, times);
  }

  /**
   * Shows a pop-up menu at the given column.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnIndex the index of the column.
   * @return the displayed pop-up menu.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   * @throws org.uitest4j.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   */
  @RunsInEDT
  @Nonnull public JPopupMenu showPopupMenu(@Nonnull JTableHeader tableHeader, int columnIndex) {
    return robot.showPopupMenu(tableHeader, pointAtIndex(tableHeader, columnIndex, location));
  }

  @RunsInEDT
  @Nonnull private static Point pointAtIndex(final @Nonnull JTableHeader tableHeader, final int columnIndex,
                                             final JTableHeaderLocation location) {
    Point result = execute(() -> {
      Point p = location.pointAt(tableHeader, columnIndex);
      checkEnabledAndShowing(tableHeader);
      tableHeader.getTable().scrollRectToVisible(tableHeader.getHeaderRect(columnIndex));
      return p;
    });
    return checkNotNull(result);
  }

  /**
   * Shows a pop-up menu at the given column.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param columnName the name of the column. It can be a regular expression.
   * @return the displayed pop-up menu.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws org.uitest4j.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   */
  @RunsInEDT
  @Nonnull public JPopupMenu showPopupMenu(@Nonnull JTableHeader tableHeader, @Nullable String columnName) {
    return robot.showPopupMenu(tableHeader, pointAtName(tableHeader, new StringTextMatcher(columnName), location()));
  }

  /**
   * Shows a pop-up menu at the column whose name matches the given regular expression pattern.
   *
   * @param tableHeader the target {@code JTableHeader}.
   * @param pattern the regular expression pattern to match.
   * @return the displayed pop-up menu.
   * @throws IllegalStateException if the {@code JTableHeader} is disabled.
   * @throws IllegalStateException if the {@code JTableHeader} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws org.uitest4j.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   */
  @RunsInEDT
  @Nonnull public JPopupMenu showPopupMenu(@Nonnull JTableHeader tableHeader, @Nonnull Pattern pattern) {
    return robot.showPopupMenu(tableHeader, pointAtName(tableHeader, new PatternTextMatcher(pattern), location()));
  }

  @RunsInEDT
  @Nonnull private static Point pointAtName(final @Nonnull JTableHeader tableHeader,
                                            final @Nonnull TextMatcher matcher,
                                            final @Nonnull JTableHeaderLocation location) {
    Point result = execute(() -> {
      Pair<Integer, Point> indexAndLocation = location.pointAt(tableHeader, matcher);
      checkEnabledAndShowing(tableHeader);
      tableHeader.getTable().scrollRectToVisible(tableHeader.getHeaderRect(indexAndLocation.first));
      return indexAndLocation.second;
    });
    return checkNotNull(result);
  }

  @Nonnull private JTableHeaderLocation location() {
    return location;
  }
}
