/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.ComponentMatcher;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.core.TypeMatcher;
import org.uitest4j.swing.util.TimeoutWatch;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.uitest4j.swing.timing.Pause.pause;
import static org.uitest4j.swing.util.TimeoutWatch.startWatchWithTimeoutOf;

/**
 * Looks up the {@code JList} in the pop-up raised by a {@code JComboBox}, if the LAF actually uses one.
 *
 * @author Alex Ruiz
 */
final class JComboBoxDropDownListFinder {
	static final ComponentMatcher LIST_MATCHER = new TypeMatcher(JList.class);

	private final Robot robot;

	JComboBoxDropDownListFinder(Robot robot) {
		this.robot = robot;
	}

	/**
	 * Finds the {@code JList} in the pop-up raised by a {@code JComboBox}, if the LAF actually uses one.
	 *
	 * @return the found {@code JList}, or {@code null} if a drop-down list cannot be found.
	 */
	@RunsInEDT
	@Nullable
	JList<?> findDropDownList() {
		JPopupMenu popup = robot.findActivePopupMenu();
		if (popup == null) {
			TimeoutWatch watch = startWatchWithTimeoutOf(robot.settings().timeoutToFindPopup());
			popup = robot.findActivePopupMenu();
			while (popup == null) {
				if (watch.isTimeOut()) {
					return null;
				}
				pause();
				popup = robot.findActivePopupMenu();
			}
		}
		return findListIn(popup);
	}

	@Nullable
	private JList<?> findListIn(@Nonnull Container parent) {
		List<Component> found = new ArrayList<>(robot.finder().findAll(parent, LIST_MATCHER));
		if (found.size() != 1) {
			return null;
		}
		return (JList<?>) found.get(0);
	}
}
