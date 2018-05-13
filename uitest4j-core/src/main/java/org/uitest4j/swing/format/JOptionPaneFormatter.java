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

import static javax.swing.JOptionPane.*;

/**
 * Formatter for {@code JOptionPane}s.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFormatter extends ComponentFormatterTemplate {
	private static final IntEnum MESSAGE_TYPES = new IntEnum();

	static {
		MESSAGE_TYPES.put(ERROR_MESSAGE, "ERROR_MESSAGE").put(INFORMATION_MESSAGE, "INFORMATION_MESSAGE")
				.put(WARNING_MESSAGE, "WARNING_MESSAGE").put(QUESTION_MESSAGE, "QUESTION_MESSAGE")
				.put(PLAIN_MESSAGE, "PLAIN_MESSAGE");
	}

	private static final IntEnum OPTION_TYPES = new IntEnum();

	static {
		OPTION_TYPES.put(DEFAULT_OPTION, "DEFAULT_OPTION").put(YES_NO_OPTION, "YES_NO_OPTION")
				.put(YES_NO_CANCEL_OPTION, "YES_NO_CANCEL_OPTION").put(QUESTION_MESSAGE, "QUESTION_MESSAGE")
				.put(OK_CANCEL_OPTION, "OK_CANCEL_OPTION");
	}

	/**
	 * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JOptionPane}.
	 *
	 * @param c the given {@code Component}.
	 * @return the {@code String} representation of the given {@code JOptionPane}.
	 */
	@RunsInCurrentThread
	@Override
	@Nonnull
	protected String doFormat(@Nonnull Component c) {
		JOptionPane optionPane = (JOptionPane) c;
		String format = "%s[message='%s', messageType=%s, optionType=%s, enabled=%b, visible=%b, showing=%b]";
		return String.format(format, getRealClassName(c), optionPane.getMessage(),
				MESSAGE_TYPES.get(optionPane.getMessageType()), OPTION_TYPES.get(optionPane.getOptionType()),
				optionPane.isEnabled(), optionPane.isVisible(), optionPane.isShowing());
	}

	/**
	 * @return {@code JOptionPane.class}.
	 */
	@Override
	@Nonnull
	public Class<? extends Component> targetType() {
		return JOptionPane.class;
	}
}
