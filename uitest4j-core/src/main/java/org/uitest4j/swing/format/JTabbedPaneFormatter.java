/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.format;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.uitest4j.swing.util.Strings.singleQuote;

/**
 * Formatter for {@code JTabbedPane}s.
 *
 * @author Alex Ruiz
 */
public class JTabbedPaneFormatter extends ComponentFormatterTemplate {
	private static final String NO_SELECTION = "<No selection>";

	/**
	 * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JTabbedPane}.
	 *
	 * @param c the given {@code Component}.
	 * @return the {@code String} representation of the given {@code JTabbedPane}.
	 */
	@Override
	@Nonnull
	protected String doFormat(@Nonnull Component c) {
		JTabbedPane tabbedPane = (JTabbedPane) c;
		String format = "%s[name='%s', selectedTabIndex=%d, selectedTabTitle=%s, tabCount=%d, tabTitles=%s, enabled=%b, visible=%s, showing=%s";
		return String.format(format, getRealClassName(c), tabbedPane.getName(),
				tabbedPane.getSelectedIndex(), selectedTab(tabbedPane), tabbedPane.getTabCount(),
				Arrays.toString(tabTitles(tabbedPane)), tabbedPane.isEnabled(), tabbedPane.isVisible(),
				tabbedPane.isShowing());
	}

	private String selectedTab(JTabbedPane tabbedPane) {
		if (tabbedPane.getTabCount() == 0) {
			return NO_SELECTION;
		}
		int index = tabbedPane.getSelectedIndex();
		if (index == -1) {
			return NO_SELECTION;
		}
		return singleQuote(tabbedPane.getTitleAt(index));
	}

	private String[] tabTitles(JTabbedPane tabbedPane) {
		int count = tabbedPane.getTabCount();
		if (count == 0) {
			return new String[0];
		}
		String[] titles = new String[count];
		for (int i = 0; i < count; i++) {
			titles[i] = tabbedPane.getTitleAt(i);
		}
		return titles;
	}

	/**
	 * @return {@code JTabbedPane.class}.
	 */
	@Override
	@Nonnull
	public Class<? extends Component> targetType() {
		return JTabbedPane.class;
	}
}
