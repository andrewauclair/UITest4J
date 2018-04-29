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

import org.uitest4j.swing.core.Robot;
import org.assertj.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * <p>
 * Supports functional testing of {@code JScrollPane}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 * 
 * @author Yvonne Wang
 */
@InternalApi
public class JScrollPaneDriver extends JComponentDriver {
  /**
   * Creates a new {@link JScrollPaneDriver}.
   * 
   * @param robot the robot the robot to use to simulate user input.
   */
  public JScrollPaneDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Returns the horizontal {@code JScrollBar} in the given {@code JScrollPane}.
   * 
   * @param scrollPane the given {@code JScrollBar}.
   * @return the horizontal scroll bar in the given {@code JScrollBar}.
   */
  @RunsInEDT
  @Nonnull public JScrollBar horizontalScrollBarIn(@Nonnull JScrollPane scrollPane) {
    return horizontalScrollBar(scrollPane);
  }

  @RunsInEDT
  @Nonnull private static JScrollBar horizontalScrollBar(final @Nonnull JScrollPane scrollPane) {
    JScrollBar result = execute(() -> scrollPane.getHorizontalScrollBar());
    return checkNotNull(result);
  }

  /**
   * Returns the vertical {@code JScrollBar} in the given {@code JScrollPane}.
   * 
   * @param scrollPane the given {@code JScrollBar}.
   * @return the vertical scroll bar in the given {@code JScrollBar}.
   */
  @RunsInEDT
  @Nonnull public JScrollBar verticalScrollBarIn(@Nonnull JScrollPane scrollPane) {
    return verticalScrollBar(scrollPane);
  }

  @RunsInEDT
  @Nonnull private static JScrollBar verticalScrollBar(final @Nonnull JScrollPane scrollPane) {
    JScrollBar result = execute(() -> scrollPane.getVerticalScrollBar());
    return checkNotNull(result);
  }
}
