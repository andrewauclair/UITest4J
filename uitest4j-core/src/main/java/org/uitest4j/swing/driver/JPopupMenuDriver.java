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
import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.internal.annotation.InternalApi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static org.uitest4j.swing.driver.JPopupMenuElementsAsTextQuery.menuElementsAsText;

/**
 * <p>
 * Supports functional testing of {@code JPopupMenu}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JPopupMenuDriver extends JComponentDriver {
	/**
	 * Creates a new {@link JPopupMenuDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JPopupMenuDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Returns the contents of the pop-up menu as a {@code String} array.
	 *
	 * @param popupMenu the target {@code JPopupMenu}.
	 * @return the contents of the pop-up menu as a {@code String} array.
	 */
	@RunsInEDT
	@Nonnull
	public String[] menuLabelsOf(@Nonnull JPopupMenu popupMenu) {
		return menuElementsAsText(popupMenu);
	}

	/**
	 * Finds a {@code JMenuItem}, contained in the {@code Container}, which name matches the specified one.
	 *
	 * @param popupMenu the target {@code JPopupMenu}.
	 * @param name      the name to match.
	 * @return the {@code JMenuItem} found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a {@code JMenuItem} having a matching name could
	 *                                                               not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one {@code JMenuItem} having a matching
	 *                                                               name is found.
	 */
	@RunsInEDT
	@Nonnull
	public JMenuItem menuItem(@Nonnull JPopupMenu popupMenu, @Nullable String name) {
		return robot.finder().findByName(popupMenu, name, JMenuItem.class, false);
	}

	/**
	 * Finds a {@code JMenuItem}, contained in the {@code Container}, that matches the specified search criteria.
	 *
	 * @param popupMenu the target {@code JPopupMenu}.
	 * @param matcher   contains the search criteria for finding a {@code JMenuItem}.
	 * @return the {@code JMenuItem} found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a {@code JMenuItem} that matches the given search
	 *                                                               criteria could not be found. Or if more than one {@code JMenuItem} that matches the given search criteria
	 *                                                               is found.
	 */
	@Nonnull
	public JMenuItem menuItem(@Nonnull JPopupMenu popupMenu,
							  @Nonnull GenericTypeMatcher<? extends JMenuItem> matcher) {
		return robot.finder().find(popupMenu, matcher);
	}
}
