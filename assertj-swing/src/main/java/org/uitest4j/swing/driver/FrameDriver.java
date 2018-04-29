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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.annotation.ThreadSafeAction;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.util.ToolkitProvider;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

import static java.awt.Frame.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.FrameTitleQuery.titleOf;
import static org.uitest4j.swing.driver.WindowLikeContainers.iconifyButtonLocation;
import static org.uitest4j.swing.driver.WindowLikeContainers.maximizeButtonLocation;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.exception.ActionFailedException.actionFailure;

/**
 * <p>
 * Supports functional testing of AWT or Swing {@code Frame}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Christian Rösch
 */
@InternalApi
public class FrameDriver extends WindowDriver {
	/**
	 * Creates a new {@link FrameDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public FrameDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Iconifies the given AWT or Swing {@code Frame}.
	 *
	 * @param frame the given {@code Frame}.
	 * @throws IllegalStateException if the {@code Frame} is not enabled.
	 * @throws IllegalStateException if the {@code Frame} is not showing on the screen.
	 */
	@RunsInEDT
	public void iconify(@Nonnull Frame frame) {
		moveMouseIgnoringAnyError(frame, iconifyInfo(frame));
		robot.waitForIdle();
		updateFrameExtendedState(frame, ICONIFIED);
	}

	@RunsInEDT
	private static Point iconifyInfo(final @Nonnull Frame frame) {
		return execute(() -> {
			checkEnabledAndShowing(frame);
			return iconifyButtonLocation(frame);
		});
	}

	/**
	 * Deiconifies the given AWT or Swing {@code Frame}.
	 *
	 * @param frame the given {@code Frame}.
	 * @throws IllegalStateException if the {@code Frame} is not enabled.
	 * @throws IllegalStateException if the {@code Frame} is not showing on the screen.
	 */
	@RunsInEDT
	public void deiconify(@Nonnull Frame frame) {
		checkInEdtEnabledAndShowing(frame);
		updateFrameExtendedState(frame, NORMAL);
	}

	/**
	 * Normalizes the given AWT or Swing {@code Frame}.
	 *
	 * @param frame the given {@code Frame}.
	 * @throws IllegalStateException if the {@code Frame} is not enabled.
	 * @throws IllegalStateException if the {@code Frame} is not showing on the screen.
	 */
	@RunsInEDT
	public void normalize(@Nonnull Frame frame) {
		checkInEdtEnabledAndShowing(frame);
		updateFrameExtendedState(frame, NORMAL);
	}

	/**
	 * Makes the AWT or Swing {@code Frame} full size.
	 *
	 * @param frame the target {@code Frame}.
	 * @throws IllegalStateException                              if the {@code Frame} is not enabled.
	 * @throws IllegalStateException                              if the {@code Frame} is not showing on the screen.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the operating system does not support maximizing
	 *                                                            frames.
	 */
	@RunsInEDT
	public void maximize(@Nonnull Frame frame) {
		moveMouseIgnoringAnyError(frame, maximizeInfo(frame));
		if (!supportsMaximize(ToolkitProvider.instance().defaultToolkit())) {
			throw actionFailure("Platform does not support maximizing frames");
		}
		updateFrameExtendedState(frame, MAXIMIZED_BOTH);
	}

	@RunsInEDT
	@Nonnull
	private static Point maximizeInfo(final @Nonnull Frame frame) {
		Point result = execute(() -> {
			checkEnabledAndShowing(frame);
			return maximizeButtonLocation(frame);
		});
		return Objects.requireNonNull(result);
	}

	@ThreadSafeAction
	private void updateFrameExtendedState(@Nonnull Frame frame, int state) {
		frame.setExtendedState(state);
		robot.waitForIdle();
	}

	private static boolean supportsMaximize(@Nonnull Toolkit toolkit) {
		return toolkit.isFrameStateSupported(MAXIMIZED_BOTH);
	}

	/**
	 * Verifies that the title of the given {@code Frame} is equal to the expected one.
	 *
	 * @param frame    the target {@code Frame}.
	 * @param expected the expected title.
	 * @throws AssertionError if the title of the given {@code Frame} is not equal to the expected one.
	 */
	@RunsInEDT
	public void requireTitle(@Nonnull Frame frame, String expected) {
		String actual = titleOf(frame);
		assertThat(actual).as(propertyName(frame, "title")).isEqualTo(expected);
	}
}
