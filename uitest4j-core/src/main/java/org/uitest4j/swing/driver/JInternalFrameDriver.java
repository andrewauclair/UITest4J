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
import org.uitest4j.swing.exception.UnexpectedException;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.util.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.JInternalFrame.JDesktopIcon;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.Objects;

import static org.uitest4j.swing.driver.ComponentPreconditions.checkShowing;
import static org.uitest4j.swing.driver.JInternalFrameAction.*;
import static org.uitest4j.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.uitest4j.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.uitest4j.swing.driver.JInternalFrameSetMaximumTask.setMaximum;
import static org.uitest4j.swing.driver.JInternalFrameTitleQuery.titleOf;
import static org.uitest4j.swing.driver.WindowLikeContainers.*;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.exception.ActionFailedException.actionFailure;
import static org.uitest4j.swing.format.Formatting.format;

/**
 * <p>
 * Supports functional testing of {@code JInternalFrame}s.
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
public class JInternalFrameDriver extends JComponentDriver {
	/**
	 * Creates a new {@link JInternalFrameDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JInternalFrameDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Brings the given {@code JInternalFrame} to the front.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 */
	@RunsInEDT
	public void moveToFront(final @Nonnull JInternalFrame internalFrame) {
		// it seems that moving to front always works, regardless if the internal frame is invisible and/or
// disabled.
		execute(internalFrame::toFront);
	}

	/**
	 * Brings the given {@code JInternalFrame} to the back.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 */
	@RunsInEDT
	public void moveToBack(final @Nonnull JInternalFrame internalFrame) {
		// it seems that moving to back always works, regardless if the internal frame is invisible and/or
// disabled.
		execute(internalFrame::moveToBack);
	}

	/**
	 * Maximises the given {@code JInternalFrame}, deconifying it first if it is iconified.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @throws IllegalStateException                              if the {@code JInternalFrame} is not maximisable.
	 * @throws IllegalStateException                              if the {@code JInternalFrame} is not showing on the screen.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the {@code JInternalFrame} vetoes the action.
	 */
	@RunsInEDT
	public void maximize(@Nonnull JInternalFrame internalFrame) {
		Pair<Container, Point> maximizeLocation = maximizeLocationOf(internalFrame);
		maximizeOrNormalize(internalFrame, MAXIMIZE, maximizeLocation);
	}

	@RunsInEDT
	@Nonnull
	private static Pair<Container, Point> maximizeLocationOf(final @Nonnull JInternalFrame internalFrame) {
		Pair<Container, Point> result = execute(new GuiQuery<>() {
			@Override
			@Nullable
			protected Pair<Container, Point> executeInEDT() {
				checkCanMaximize(internalFrame);
				return findMaximizeLocation(internalFrame);
			}
		});
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	private static void checkCanMaximize(@Nonnull JInternalFrame internalFrame) {
		checkShowingOrIconified(internalFrame);
		if (!internalFrame.isMaximizable()) {
			String msg = String.format("The JInternalFrame <%s> is not maximizable", format(internalFrame));
			throw new IllegalStateException(msg);
		}
	}

	/**
	 * Normalises the given {@code JInternalFrame}, deconifying it first if it is iconified.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @throws IllegalStateException                              if the {@code JInternalFrame} is not showing on the screen.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the {@code JInternalFrame} vetoes the action.
	 */
	@RunsInEDT
	public void normalize(@Nonnull JInternalFrame internalFrame) {
		Pair<Container, Point> normalizeLocation = validateAndFindNormalizeLocation(internalFrame);
		maximizeOrNormalize(internalFrame, NORMALIZE, normalizeLocation);
	}

	@RunsInEDT
	private static Pair<Container, Point> validateAndFindNormalizeLocation(final @Nonnull JInternalFrame internalFrame) {
		return execute(new GuiQuery<>() {
			@Override
			protected Pair<Container, Point> executeInEDT() {
				checkShowingOrIconified(internalFrame);
				return findMaximizeLocation(internalFrame);
			}
		});
	}

	@RunsInCurrentThread
	private static void checkShowingOrIconified(@Nonnull JInternalFrame internalFrame) {
		if (!internalFrame.isIcon()) {
			checkShowing(internalFrame);
		}
	}

	@RunsInCurrentThread
	@Nonnull
	private static Pair<Container, Point> findMaximizeLocation(@Nonnull JInternalFrame internalFrame) {
		Container clickTarget = internalFrame.isIcon() ? internalFrame.getDesktopIcon() : internalFrame;
		Point location = maximizeButtonLocation(Objects.requireNonNull(clickTarget));
		return Pair.of(clickTarget, location);
	}

	@RunsInEDT
	private void maximizeOrNormalize(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action,
									 @Nonnull Pair<Container, Point> toMoveMouseTo) {
		moveMouseIgnoringAnyError(toMoveMouseTo.first, toMoveMouseTo.second);
		setMaximumProperty(internalFrame, action);
	}

	@RunsInEDT
	private void setMaximumProperty(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action) {
		try {
			setMaximum(internalFrame, action);
			robot.waitForIdle();
		}
		catch (UnexpectedException unexpected) {
			failIfVetoed(internalFrame, action, unexpected);
		}
	}

	/**
	 * Iconifies the given {@code JInternalFrame}.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @throws IllegalStateException                              if the {@code JInternalFrame} is not showing on the screen.
	 * @throws IllegalStateException                              if the {@code JInternalFrame} is not iconifiable.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the {@code JInternalFrame} vetoes the action.
	 */
	@RunsInEDT
	public void iconify(@Nonnull JInternalFrame internalFrame) {
		Pair<Boolean, Point> iconifyInfo = findIconifyInfo(internalFrame);
		if (iconifyInfo.first) {
			return; // internal frame is already iconified
		}
		moveMouseIgnoringAnyError(internalFrame, iconifyInfo.second);
		setIconProperty(internalFrame, ICONIFY);
	}

	@RunsInEDT
	@Nonnull
	private static Pair<Boolean, Point> findIconifyInfo(final @Nonnull JInternalFrame internalFrame) {
		Pair<Boolean, Point> result = execute(new GuiQuery<>() {
			@Override
			@Nullable
			protected Pair<Boolean, Point> executeInEDT() throws Throwable {
				checkShowingOrIconified(internalFrame);
				if (!internalFrame.isIconifiable()) {
					String msg = String.format("The JInternalFrame <%s> is not iconifiable.", format(internalFrame));
					throw new IllegalStateException(msg);
				}
				return iconifyInfo(internalFrame);
			}
		});
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	@Nonnull
	private static Pair<Boolean, Point> iconifyInfo(@Nonnull JInternalFrame internalFrame) {
		boolean iconified = isIconified(internalFrame);
		if (iconified) {
			return Pair.of(true, null);
		}
		return Pair.of(iconified, findIconifyLocation(internalFrame));
	}

	/**
	 * De-iconifies the given {@code JInternalFrame}.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @throws IllegalStateException                              if the {@code JInternalFrame} is not showing on the screen.
	 * @throws org.uitest4j.swing.exception.ActionFailedException if the {@code JInternalFrame} vetoes the action.
	 */
	@RunsInEDT
	public void deiconify(@Nonnull JInternalFrame internalFrame) {
		Triple<Boolean, Container, Point> deiconifyInfo = validateAndfindDeiconifyInfo(internalFrame);
		if (deiconifyInfo.first) {
			return; // internal frame is already de-iconified
		}
		moveMouseIgnoringAnyError(deiconifyInfo.second, deiconifyInfo.third);
		setIconProperty(internalFrame, DEICONIFY);
	}

	@RunsInEDT
	@Nonnull
	private static Triple<Boolean, Container, Point> validateAndfindDeiconifyInfo(
			final @Nonnull JInternalFrame internalFrame) {
		Triple<Boolean, Container, Point> result = execute(new GuiQuery<>() {
			@Override
			@Nullable
			protected Triple<Boolean, Container, Point> executeInEDT() throws Throwable {
				checkShowingOrIconified(internalFrame);
				return deiconifyInfo(internalFrame);
			}
		});
		return Objects.requireNonNull(result);
	}

	@RunsInCurrentThread
	@Nonnull
	private static Triple<Boolean, Container, Point> deiconifyInfo(@Nonnull JInternalFrame internalFrame) {
		boolean deiconified = !isIconified(internalFrame);
		if (deiconified) {
			return Triple.of(true, null, null);
		}
		Container desktopIcon = Objects.requireNonNull(internalFrame.getDesktopIcon());
		return Triple.of(deiconified, desktopIcon, iconifyButtonLocation(desktopIcon));
	}

	@RunsInCurrentThread
	@Nonnull
	private static Point findIconifyLocation(JInternalFrame internalFrame) {
		JDesktopIcon desktopIcon = Objects.requireNonNull(internalFrame.getDesktopIcon());
		return iconifyButtonLocation(desktopIcon);
	}

	@RunsInEDT
	private void setIconProperty(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action) {
		try {
			setIcon(internalFrame, action);
			robot.waitForIdle();
		}
		catch (UnexpectedException unexpected) {
			failIfVetoed(internalFrame, action, unexpected);
		}
	}

	// Used for tests
	void failIfVetoed(@Nonnull JInternalFrame internalFrame, @Nonnull JInternalFrameAction action,
					  @Nonnull UnexpectedException unexpected) {
		PropertyVetoException vetoError = vetoFrom(unexpected);
		if (vetoError == null) {
			return;
		}
		String msg = String.format("%s of %s was vetoed: <%s>", action.name, format(internalFrame), vetoError.getMessage());
		throw actionFailure(msg);
	}

	@Nullable
	private PropertyVetoException vetoFrom(@Nonnull UnexpectedException unexpected) {
		Throwable cause = unexpected.getCause();
		if (!(cause instanceof PropertyVetoException)) {
			return null;
		}
		return (PropertyVetoException) cause;
	}

	/**
	 * Resizes the {@code JInternalFrame} horizontally.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @param width         the width that the {@code JInternalFrame} should have after being resized.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not resizable by the user.
	 */
	@RunsInEDT
	public void resizeWidth(@Nonnull JInternalFrame internalFrame, int width) {
		doResizeWidth(internalFrame, width);
	}

	/**
	 * Resizes the {@code JInternalFrame} vertically.
	 *
	 * @param w      the target {@code JInternalFrame}.
	 * @param height the height that the {@code JInternalFrame} should have after being resized.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not resizable by the user.
	 */
	@RunsInEDT
	public void resizeHeight(@Nonnull JInternalFrame w, int height) {
		doResizeHeight(w, height);
	}

	/**
	 * Resizes the {@code JInternalFrame} to the given size.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @param size          the size to resize the {@code JInternalFrame} to.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not resizable by the user.
	 */
	@RunsInEDT
	public void resizeTo(@Nonnull JInternalFrame internalFrame, @Nonnull Dimension size) {
		resize(internalFrame, size.width, size.height);
	}

	/**
	 * Moves the {@code JInternalFrame} to the given location.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @param where         the location to move the {@code JInternalFrame} to.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
	 */
	@RunsInEDT
	public void move(@Nonnull JInternalFrame internalFrame, @Nonnull Point where) {
		move(internalFrame, where.x, where.y);
	}

	/**
	 * Closes the given {@code JInternalFrame}.
	 *
	 * @param internalFrame the target {@code JInternalFrame}.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not showing on the screen.
	 * @throws IllegalStateException if the {@code JInternalFrame} is not closable.
	 */
	@RunsInEDT
	public void close(@Nonnull JInternalFrame internalFrame) {
		Point closeButtonLocation = findCloseButtonLocation(internalFrame);
		if (closeButtonLocation == null) {
			return; // internal frame is already closed
		}
		moveMouseIgnoringAnyError(internalFrame, closeButtonLocation);
		JInternalFrameCloseTask.close(internalFrame);
		robot.waitForIdle();
	}

	@RunsInEDT
	@Nullable
	private static Point findCloseButtonLocation(final @Nonnull JInternalFrame internalFrame) {
		return execute(() -> {
			checkShowing(internalFrame);
			if (!internalFrame.isClosable()) {
				String msg = String.format("The JInternalFrame <%s> is not closable", format(internalFrame));
				throw new IllegalStateException(msg);
			}
			if (internalFrame.isClosed()) {
				return null;
			}
			return closeButtonLocation(internalFrame);
		});
	}

	/**
	 * Verifies that the title of the given {@code JInternalFrame} is equal to the expected one.
	 *
	 * @param frame    the target {@code JInternalFrame}.
	 * @param expected the expected title.
	 * @throws AssertionError if the title of the given {@code JInternalFrame} is not equal to the expected one.
	 */
	@RunsInEDT
	public void requireTitle(@Nonnull JInternalFrame frame, String expected) {
		String actual = titleOf(frame);
		OpenTest4JAssertions.assertEquals(expected, actual,
				() -> String.format("Expected title of '%s' to be '%s' but was '%s'", frame.getName(), expected, actual));
	}
}
