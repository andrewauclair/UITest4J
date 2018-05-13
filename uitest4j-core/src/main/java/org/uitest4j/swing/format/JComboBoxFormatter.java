/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.format;

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Formatter for {@code JComboBox}es.
 *
 * @author Yvonne Wang
 */
public class JComboBoxFormatter extends ComponentFormatterTemplate {
	/**
	 * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JComboBox}.
	 *
	 * @param c the given {@code Component}.
	 * @return the {@code String} representation of the given {@code JComboBox}.
	 */
	@RunsInCurrentThread
	@Override
	@Nonnull
	protected String doFormat(@Nonnull Component c) {
		JComboBox<?> comboBox = (JComboBox<?>) c;
		String format = "%s[name='%s', selectedItem='%s', contents=%s, editable=%b, enabled=%b, visible=%b, showing=%b]";
		return String.format(format, getRealClassName(c), comboBox.getName(),
				comboBox.getSelectedItem(), Arrays.toString(contentsOf(comboBox)), comboBox.isEditable(),
				comboBox.isEnabled(), comboBox.isVisible(), comboBox.isShowing());
	}

	@RunsInCurrentThread
	@Nonnull
	private Object[] contentsOf(@Nonnull JComboBox<?> comboBox) {
		List<Object> contents = new ArrayList<>();
		int count = comboBox.getItemCount();
		for (int i = 0; i < count; i++) {
			contents.add(comboBox.getItemAt(i));
		}
		return contents.toArray();
	}

	/**
	 * @return {@code JComboBox.class}.
	 */
	@Override
	@Nonnull
	public Class<? extends Component> targetType() {
		return JComboBox.class;
	}
}
