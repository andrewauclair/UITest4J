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
package org.uitest4j.swing.driver;

import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.internal.annotation.InternalApi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.lang.Boolean.getBoolean;
import static org.uitest4j.swing.core.WindowAncestorFinder.windowAncestorOf;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JMenuPopupMenuQuery.popupMenuOf;
import static org.uitest4j.swing.driver.WindowMoveToFrontTask.toFront;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.exception.ActionFailedException.actionFailure;
import static org.uitest4j.swing.format.Formatting.format;
import static org.uitest4j.swing.timing.Pause.pause;
import static org.uitest4j.swing.util.Platform.isOSX;

/**
 * <p>
 * Supports functional testing of {@code JMenuItem}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public class JMenuItemDriver extends JComponentDriver {
	/**
	 * Creates a new {@link JMenuItemDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JMenuItemDriver(@Nonnull SwingRobot robot) {
		super(robot);
	}

	@Override
	public void click(@Nonnull Component c) {
		/*
		 * Since our internal methods now all call click(Component) this is used to route the method calls to the correct
		 * method (either the parent method or the click(JMenuItem)).
		 */
		if (c instanceof JMenuItem) {
			click((JMenuItem) c);
		}
		else {
			super.click(c);
		}
	}

	/**
	 * Finds and selects the given {@code JMenuItem}.
	 *
	 * @param menuItem the {@code JMenuItem} to select.
	 * @throws IllegalStateException                              if {@link org.uitest4j.swing.core.Settings#clickOnDisabledComponentsAllowed()} is <code>false</code> and the
	 *                                                            menu to select is disabled.
	 * @throws IllegalStateException                              if the menu to select is not showing on the screen.
	 * @throws ActionFailedException if the menu has a pop-up and it fails to show up.
	 */
	@RunsInEDT
	public void click(@Nonnull JMenuItem menuItem) {
		show(menuItem);
		doClick(menuItem);
		ensurePopupIsShowing(menuItem);
	}

	@RunsInEDT
	private void show(@Nonnull JMenuItem menuItem) {
		JMenuItemLocation location = locationOf(menuItem);
		activateParentIfIsMenu(location);
		moveParentWindowToFront(location);
		if (menuItem instanceof JMenu && !location.inMenuBar()) {
			waitForSubMenuToShow();
		}
	}

	@RunsInEDT
	@Nonnull
	private static JMenuItemLocation locationOf(final @Nonnull JMenuItem menuItem) {
		JMenuItemLocation result = execute(() -> new JMenuItemLocation(menuItem));
		return Objects.requireNonNull(result);
	}

	@RunsInEDT
	private void activateParentIfIsMenu(@Nonnull JMenuItemLocation location) {
		if (!location.isParentAMenu()) {
			return;
		}
		Component c = location.parentOrInvoker();
		if (c instanceof JMenuItem) {
			click((JMenuItem) c);
		}
	}

	@RunsInEDT
	private void moveParentWindowToFront(@Nonnull JMenuItemLocation location) {
		if (!location.inMenuBar()) {
			return;
		}
		// TODO windowAncestorOf is not being called in EDT
		moveToFront(windowAncestorOf(location.parentOrInvoker()));
	}

	@RunsInEDT
	private void doClick(@Nonnull JMenuItem menuItem) {
		if (isMacOSMenuBar(menuItem)) {
			validateAndDoClick(menuItem);
			return;
		}
		super.click(menuItem);
		robot.waitForIdle();
	}

	private boolean isMacOSMenuBar(@Nonnull JMenuItem menuItem) {
		return menuItem instanceof JMenu && locationOf(menuItem).inMenuBar() && isOSX()
				&& (getBoolean("apple.laf.useScreenMenuBar") || getBoolean("com.apple.macos.useScreenMenuBar"));
	}

	@RunsInEDT
	private static void validateAndDoClick(final @Nonnull JMenuItem menuItem) {
		execute(() -> {
			checkEnabledAndShowing(menuItem);
			menuItem.doClick();
		});
	}

	@RunsInEDT
	private void ensurePopupIsShowing(@Nonnull JMenuItem menuItem) {
		if (!(menuItem instanceof JMenu)) {
			return;
		}
		JPopupMenu popup = popupMenuOf((JMenu) menuItem);
		// TODO review EDT access
		if (!waitForShowing(popup, robot.settings().timeoutToFindPopup())) {
			throw actionFailure("Clicking on menu item <" + format(menuItem) + "> never showed a pop-up menu");
		}
		waitForSubMenuToShow();
	}

	private void waitForSubMenuToShow() {
		pause(robot.settings().timeoutToFindSubMenu());
	}

	@RunsInEDT
	private void moveToFront(@Nullable Window w) {
		if (w == null) {
			return;
		}
		// Make sure the window is in front, or its menus may be obscured by another window.
		toFront(w);
		robot.waitForIdle();
		robot.moveMouse(w);
	}
}
