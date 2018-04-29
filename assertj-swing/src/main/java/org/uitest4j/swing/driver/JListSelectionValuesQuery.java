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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JListCellReader;
import org.uitest4j.swing.edt.GuiQuery;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns an array of {@code String}s that represents the selection of a given {@code JList}. This query is executed in
 * the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JListSelectionValuesQuery {
	@RunsInEDT
	static @Nonnull
	List<String> selectionValues(final @Nonnull JList<?> list, final @Nonnull JListCellReader cellReader) {
		List<String> result = execute(new GuiQuery<>() {
			@Override
			protected List<String> executeInEDT() {
				List<String> values = new ArrayList<>();
				int[] selectedIndices = list.getSelectedIndices();
				for (int index : selectedIndices) {
					values.add(cellReader.valueAt(list, index));
				}
				return values;
			}
		});
		return Objects.requireNonNull(result);
	}

	private JListSelectionValuesQuery() {
	}
}
