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

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JMenuItem}s.
 *
 * @author Alex Ruiz
 */
public final class JMenuItems {
	private JMenuItems() {
	}

	public static JMenuItemFactory menuItem() {
		return new JMenuItemFactory();
	}

	public static class JMenuItemFactory {
		String name;
		boolean selected;
		String text;

		public JMenuItemFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JMenuItemFactory selected(boolean isSelected) {
			selected = isSelected;
			return this;
		}

		public JMenuItemFactory withText(String newText) {
			text = newText;
			return this;
		}

		@RunsInEDT
		public JMenuItem createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JMenuItem executeInEDT() {
					JMenuItem menuItem = new JMenuItem();
					menuItem.setName(name);
					menuItem.setSelected(selected);
					menuItem.setText(text);
					return menuItem;
				}
			});
		}
	}
}