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

import org.uitest4j.swing.cell.JListCellReader;
import org.uitest4j.swing.edt.GuiQuery;
import org.assertj.swing.util.Pair;
import org.assertj.swing.util.TextMatcher;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.uitest4j.swing.driver.JListCellCenterQuery.cellCenter;
import static org.uitest4j.swing.driver.JListItemPreconditions.checkIndexInBounds;
import static org.uitest4j.swing.driver.JListMatchingItemQuery.matchingItemIndex;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Performs scrolling to a specific element in a {@code JList}.
 *
 * @author Alex Ruiz
 */
final class JListScrollToItemTask {
  static final Pair<Integer, Point> ITEM_NOT_FOUND = Pair.of(-1, null);

  /** @return the point that the JList was scrolled to. */
  @RunsInEDT
  static @Nonnull Point scrollToItem(final @Nonnull JList<?> list, final int index) {
    Point result = execute(() -> {
      checkEnabledAndShowing(list);
      checkIndexInBounds(list, index);
      return scrollToItemWithIndex(list, index);
    });
    return checkNotNull(result);
  }

  /** @return the index of first matching element and the point that the JList was scrolled to. */
  @RunsInEDT
  static @Nonnull Pair<Integer, Point> scrollToItem(final @Nonnull JList<?> list, final @Nonnull TextMatcher matcher,
                                                    final @Nonnull JListCellReader cellReader) {
    Pair<Integer, Point> result = execute(new GuiQuery<Pair<Integer, Point>>() {
      @Override
      protected Pair<Integer, Point> executeInEDT() {
        checkEnabledAndShowing(list);
        int index = matchingItemIndex(list, matcher, cellReader);
        if (index < 0) {
          return ITEM_NOT_FOUND;
        }
        return Pair.of(index, scrollToItemWithIndex(list, index));
      }
    });
    return checkNotNull(result);
  }

  /** @return the index of first matching element and the point that the JList was scrolled to. */
  @RunsInEDT
  static @Nonnull Pair<Integer, Point> scrollToItemIfNotSelectedYet(final @Nonnull JList<?> list,
                                                                    final @Nonnull TextMatcher matcher,
                                                                    final @Nonnull JListCellReader cellReader) {
    Pair<Integer, Point> result = execute(new GuiQuery<Pair<Integer, Point>>() {
      @Override
      protected Pair<Integer, Point> executeInEDT() {
        checkEnabledAndShowing(list);
        int index = matchingItemIndex(list, matcher, cellReader);
        if (index < 0) {
          return ITEM_NOT_FOUND;
        }
        return Pair.of(index, scrollToItemWithIndexIfNotSelectedYet(list, index));
      }
    });
    return checkNotNull(result);
  }

  /** @return the point that the JList was scrolled to. */
  @RunsInEDT
  static @Nullable Point scrollToItemIfNotSelectedYet(final @Nonnull JList<?> list, final int index) {
    return execute(() -> {
      checkEnabledAndShowing(list);
      checkIndexInBounds(list, index);
      return scrollToItemWithIndexIfNotSelectedYet(list, index);
    });
  }

  /** @return the point that the JList was scrolled to. */
  @RunsInCurrentThread
  @Nullable private static Point scrollToItemWithIndexIfNotSelectedYet(final @Nonnull JList<?> list, final int index) {
    if (list.getSelectedIndex() == index) {
      return null;
    }
    return scrollToItemWithIndex(list, index);
  }

  @RunsInCurrentThread
  @Nonnull private static Point scrollToItemWithIndex(@Nonnull JList<?> list, int index) {
    Rectangle cellBounds = checkNotNull(cellBounds(list, index));
    list.scrollRectToVisible(cellBounds);
    return cellCenter(list, cellBounds);
  }

  private JListScrollToItemTask() {
  }
}
