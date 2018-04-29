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

import org.uitest4j.swing.cell.JComboBoxCellReader;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the selected value of a {@code JComboBox} as plain text. This query is executed in the event dispatch thread
 * (EDT).
 *
 * @author Alex Ruiz
 */
final class JComboBoxSelectionValueQuery {
  private static final Pair<Boolean, String> NO_SELECTION = Pair.of(false, null);

  @RunsInEDT
  static @Nonnull Pair<Boolean, String> selection(final @Nonnull JComboBox<?> comboBox,
                                                  final @Nonnull JComboBoxCellReader cellReader) {
    Pair<Boolean, String> result = execute(new GuiQuery<Pair<Boolean, String>>() {
      @Override
      @Nullable protected Pair<Boolean, String> executeInEDT() {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex == -1) {
          return valueForNoSelection(comboBox);
        }
        return selection(cellReader.valueAt(comboBox, selectedIndex));
      }
    });
    return checkNotNull(result);
  }

  @Nonnull private static Pair<Boolean, String> valueForNoSelection(@Nonnull JComboBox<?> comboBox) {
    if (!comboBox.isEditable()) {
      return NO_SELECTION;
    }
    Object selectedItem = comboBox.getSelectedItem();
    if (selectedItem instanceof String) {
      return selection((String) selectedItem);
    }
    if (selectedItem != null) {
      return selection(selectedItem.toString());
    }
    return NO_SELECTION;
  }

  @Nonnull private static Pair<Boolean, String> selection(@Nullable String selection) {
    return Pair.of(true, selection);
  }

  private JComboBoxSelectionValueQuery() {
  }
}
