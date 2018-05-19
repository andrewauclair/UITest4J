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
package org.uitest4j.swing.fixture;

import org.opentest4j.AssertionFailedError;
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.core.ComponentMatcher;
import org.uitest4j.swing.driver.JMenuItemMatcher;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Looks up of {@code JMenuItem}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JMenuItemFinder {
	private final SwingRobot robot;
	private final Container target;

	JMenuItemFinder(@Nonnull SwingRobot robot, @Nonnull Container target) {
		this.robot = robot;
		this.target = target;
	}

	@Nonnull
	JMenuItem menuItemWithPath(@Nonnull String... path) {
		ComponentMatcher m = new JMenuItemMatcher(path);
		Component item = robot.finder().find(target, m);
		if (!JMenuItem.class.isAssignableFrom(item.getClass())) {
			throw new AssertionFailedError("Item is not a JMenuItem: " + item.getClass());
		}
		return (JMenuItem) item;
	}
}
