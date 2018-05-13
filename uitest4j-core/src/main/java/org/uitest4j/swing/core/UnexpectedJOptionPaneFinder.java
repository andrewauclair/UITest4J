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
package org.uitest4j.swing.core;

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.uitest4j.swing.format.Formatting.format;

/**
 * Finds {@code JOptionPane}s that are showing up on the screen and are not expected.
 *
 * @author Alex Ruiz
 */
class UnexpectedJOptionPaneFinder {
	static final ComponentMatcher OPTION_PANE_MATCHER = new TypeMatcher(JOptionPane.class, true);

	private final ComponentFinder finder;

	UnexpectedJOptionPaneFinder(@Nonnull ComponentFinder finder) {
		this.finder = finder;
	}

	@RunsInEDT
	void requireNoJOptionPaneIsShowing() {
		List<Component> found = findAll();
		if (!found.isEmpty()) {
			unexpectedJOptionPanesFound(found);
		}
	}

	private List<Component> findAll() {
		return new ArrayList<>(finder.findAll(UnexpectedJOptionPaneFinder.OPTION_PANE_MATCHER));
	}

	private void unexpectedJOptionPanesFound(@Nonnull List<Component> found) {
		StringBuilder message = new StringBuilder();
		message.append("Expecting no JOptionPane to be showing, but found:<[");
		int size = found.size();
		for (int i = 0; i < size; i++) {
			message.append(format(found.get(i)));
			if (i != size - 1) {
				message.append(", ");
			}
		}
		message.append("]>");

		throw new AssertionFailedError(message.toString());
	}
}
