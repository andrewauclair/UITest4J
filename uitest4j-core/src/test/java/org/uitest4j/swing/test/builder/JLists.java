/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.builder;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;

import javax.swing.*;

import static org.assertj.core.util.Arrays.isNullOrEmpty;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JList}s.
 *
 * @author Alex Ruiz
 */
public final class JLists {
	private JLists() {
	}

	public static JListFactory list() {
		return new JListFactory();
	}

	public static class JListFactory {
		Object items[];
		String name;
		int[] selectedIndices;
		int selectionMode;

		public JListFactory withItems(Object... newItems) {
			items = newItems;
			return this;
		}

		public JListFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JListFactory withSelectedIndices(int... newSelectedIndices) {
			selectedIndices = newSelectedIndices;
			return this;
		}

		public JListFactory withSelectionMode(int newSelectionMode) {
			selectionMode = newSelectionMode;
			return this;
		}

		@RunsInEDT
		public JList createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JList executeInEDT() {
					JList list = new JList();
					if (!isNullOrEmpty(items)) {
						list.setModel(modelWith(items));
					}
					list.setName(name);
					if (selectedIndices != null) {
						list.setSelectedIndices(selectedIndices);
					}
					list.setSelectionMode(selectionMode);
					return list;
				}
			});
		}

		static ListModel modelWith(Object[] items) {
			DefaultListModel model = new DefaultListModel();
			for (Object item : items) {
				model.addElement(item);
			}
			return model;
		}
	}
}