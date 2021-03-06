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
 * Factory of {@code JRadioButton}s.
 *
 * @author Alex Ruiz
 */
public final class JRadioButtons {
	private JRadioButtons() {
	}

	public static JRadioButtonFactory radioButton() {
		return new JRadioButtonFactory();
	}

	public static class JRadioButtonFactory {
		String name;
		boolean selected;
		String text;

		public JRadioButtonFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JRadioButtonFactory selected(boolean isSelected) {
			selected = isSelected;
			return this;
		}

		public JRadioButtonFactory withText(String newText) {
			text = newText;
			return this;
		}

		@RunsInEDT
		public JRadioButton createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JRadioButton executeInEDT() {
					JRadioButton radioButton = new JRadioButton();
					radioButton.setName(name);
					radioButton.setSelected(selected);
					radioButton.setText(text);
					return radioButton;
				}
			});
		}
	}
}