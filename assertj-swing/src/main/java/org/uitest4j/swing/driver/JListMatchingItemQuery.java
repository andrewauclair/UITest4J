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
import org.uitest4j.swing.cell.JListCellReader;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.util.StringTextMatcher;
import org.uitest4j.swing.util.TextMatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Collections.sort;
import static org.uitest4j.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.uitest4j.swing.driver.JListCellCenterQuery.cellCenter;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Looks up the first item in a {@code JList} whose value matches a given one. This query is executed in the event
 * dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JListMatchingItemQuery {
	@RunsInEDT
	static @Nonnull
	Point centerOfMatchingItemCell(final @Nonnull JList<?> list, final @Nullable String value,
								   final @Nonnull JListCellReader cellReader) {
		Point result = execute(() -> {
			int itemIndex = matchingItemIndex(list, new StringTextMatcher(value), cellReader);
			Rectangle cellBounds = Objects.requireNonNull(cellBounds(list, itemIndex));
			return cellCenter(list, cellBounds);
		});
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	static int matchingItemIndex(@Nonnull JList<?> list, @Nonnull TextMatcher matcher, @Nonnull JListCellReader cellReader) {
		int size = list.getModel().getSize();
		for (int i = 0; i < size; i++) {
			if (matcher.isMatching(cellReader.valueAt(list, i))) {
				return i;
			}
		}
		return -1;
	}

	@RunsInEDT
	static @Nonnull
	List<Integer> matchingItemIndices(final @Nonnull JList<?> list, final @Nonnull TextMatcher matcher,
									  final @Nonnull JListCellReader cellReader) {
		List<Integer> result = execute(new GuiQuery<>() {
			@Override
			protected List<Integer> executeInEDT() {
				Set<Integer> indices = new HashSet<>();
				int size = list.getModel().getSize();
				for (int i = 0; i < size; i++) {
					if (matcher.isMatching(cellReader.valueAt(list, i))) {
						indices.add(i);
					}
				}
				List<Integer> indexList = new ArrayList<>(indices);
				sort(indexList);
				return indexList;
			}
		});
		return Objects.requireNonNull(result);
	}

	@RunsInEDT
	static @Nonnull
	List<String> matchingItemValues(final @Nonnull JList<?> list, final @Nonnull TextMatcher matcher,
									final @Nonnull JListCellReader cellReader) {
		List<String> result = execute(new GuiQuery<>() {
			@Override
			protected List<String> executeInEDT() {
				List<String> values = new ArrayList<>();
				int size = list.getModel().getSize();
				for (int i = 0; i < size; i++) {
					String value = cellReader.valueAt(list, i);
					if (matcher.isMatching(value)) {
						values.add(value);
					}
				}
				return values;
			}
		});
		return Objects.requireNonNull(result);
	}

	private JListMatchingItemQuery() {
	}
}
