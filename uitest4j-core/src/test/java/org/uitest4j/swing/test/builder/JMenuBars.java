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
 * Factory of {@link JMenuBar}s.
 *
 * @author Alex Ruiz
 */
public final class JMenuBars {
	private JMenuBars() {
	}

	public static JMenuBarFactory menuBar() {
		return new JMenuBarFactory();
	}

	public static class JMenuBarFactory {
		JMenu[] menus;
		String name;

		public JMenuBarFactory withMenus(JMenu... newMenus) {
			menus = newMenus;
			return this;
		}

		public JMenuBarFactory withName(String newName) {
			name = newName;
			return this;
		}

		@RunsInEDT
		public JMenuBar createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JMenuBar executeInEDT() {
					JMenuBar menuBar = new JMenuBar();
					menuBar.setName(name);
					if (!isNullOrEmpty(menus)) {
						for (JMenu menu : menus) {
							menuBar.add(menu);
						}
					}
					return menuBar;
				}
			});
		}
	}
}