/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.util.GenericRange;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JSplitPaneLocationCalculator.locationToMoveDividerTo;
import static org.uitest4j.swing.driver.JSplitPaneSetDividerLocationTask.setDividerLocation;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * <p>
 * Supports functional testing of {@code JSplitPane}s.
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
public class JSplitPaneDriver extends JComponentDriver {
	/**
	 * Creates a new {@link JSplitPaneDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JSplitPaneDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Sets the divider position to an absolute position.
	 * <p>
	 * Since 1.2, this method respects the minimum and maximum values of the left and right components inside the given
	 * {@code JSplitPane}.
	 * </p>
	 *
	 * @param splitPane the target {@code JSplitPane}.
	 * @param location  the location to move the divider to.
	 * @throws IllegalStateException if the {@code JSplitPane} is disabled.
	 * @throws IllegalStateException if the {@code JSplitPane} is not showing on the screen.
	 */
	@RunsInEDT
	public void moveDividerTo(@Nonnull JSplitPane splitPane, int location) {
		int newLocation = locationToMoveDividerTo(splitPane, location);
		simulateMovingDivider(splitPane, newLocation);
		setDividerLocation(splitPane, newLocation);
		robot.waitForIdle();
	}

	@RunsInEDT
	private void simulateMovingDivider(@Nonnull JSplitPane split, int location) {
		if (split.getOrientation() == VERTICAL_SPLIT) {
			simulateMovingDividerVertically(split, location);
			return;
		}
		simulateMovingDividerHorizontally(split, location);
	}

	@RunsInEDT
	private void simulateMovingDividerVertically(@Nonnull JSplitPane splitPane, int location) {
		GenericRange<Point> whereToMove = findWhereToMoveDividerVertically(splitPane, location);
		simulateMovingDivider(splitPane, whereToMove);
	}

	@RunsInEDT
	@Nonnull
	private static GenericRange<Point> findWhereToMoveDividerVertically(final @Nonnull JSplitPane splitPane,
																		final int location) {
		GenericRange<Point> result = execute(new GuiQuery<>() {
			@Override
			protected GenericRange<Point> executeInEDT() {
				checkEnabledAndShowing(splitPane);
				return whereToMoveDividerVertically(splitPane, location);
			}
		});
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	@Nonnull
	private static GenericRange<Point> whereToMoveDividerVertically(@Nonnull JSplitPane splitPane, int location) {
		int x = splitPane.getWidth() / 2;
		int dividerLocation = splitPane.getDividerLocation();
		return new GenericRange<>(new Point(x, dividerLocation), new Point(x, location));
	}

	private void simulateMovingDividerHorizontally(@Nonnull JSplitPane splitPane, int location) {
		GenericRange<Point> whereToMove = findWhereToMoveDividerHorizontally(splitPane, location);
		simulateMovingDivider(splitPane, whereToMove);
	}

	@RunsInEDT
	@Nonnull
	private static GenericRange<Point> findWhereToMoveDividerHorizontally(final @Nonnull JSplitPane splitPane,
																		  final int location) {
		GenericRange<Point> result = execute(new GuiQuery<>() {
			@Override
			protected GenericRange<Point> executeInEDT() {
				checkEnabledAndShowing(splitPane);
				return whereToMoveDividerHorizontally(splitPane, location);
			}
		});
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	@Nonnull
	private static GenericRange<Point> whereToMoveDividerHorizontally(@Nonnull JSplitPane splitPane, int location) {
		int y = splitPane.getHeight() / 2;
		int dividerLocation = splitPane.getDividerLocation();
		return new GenericRange<>(new Point(dividerLocation, y), new Point(location, y));
	}

	@RunsInEDT
	private void simulateMovingDivider(@Nonnull JSplitPane splitPane, @Nonnull GenericRange<Point> range) {
		try {
			robot.moveMouse(splitPane, range.from());
			robot.pressMouseWhileRunning(LEFT_BUTTON, () -> robot.moveMouse(splitPane, range.to()));
		}
		catch (RuntimeException ignored) {
		}
	}
}
