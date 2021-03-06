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
package org.uitest4j.swing.core;

import javax.annotation.Nonnull;
import java.util.Objects;

import static org.uitest4j.swing.core.MouseButton.*;

/**
 * <p>
 * Information about a mouse button to click.
 * </p>
 *
 * <p>
 * Examples:
 * </p>
 *
 * <p>
 * Specify that the right button should be clicked once:
 * </p>
 *
 * <pre>
 * // import static org.uitest4j.swing.fixture.MouseClickInfo.*;
 * MouseClickInfo i = rightButton();
 * </pre>
 *
 * <p>
 * Specify that the left button should be clicked two times (similar to double-click):
 * </p>
 *
 * <pre>
 * // import static org.uitest4j.swing.fixture.MouseClickInfo.*;
 * MouseClickInfo i = leftButton().times(2);
 * </pre>
 *
 * @author Alex Ruiz
 */
public final class MouseClickInfo {
	private final MouseButton button;
	private int times;

	/**
	 * Specifies that the left button should be clicked once.
	 *
	 * @return the created click info.
	 */
	@Nonnull
	public static MouseClickInfo leftButton() {
		return button(LEFT_BUTTON);
	}

	/**
	 * Specifies that the middle button should be clicked once.
	 *
	 * @return the created click info.
	 */
	@Nonnull
	public static MouseClickInfo middleButton() {
		return button(MIDDLE_BUTTON);
	}

	/**
	 * Specifies that the right button should be clicked once.
	 *
	 * @return the created click info.
	 */
	@Nonnull
	public static MouseClickInfo rightButton() {
		return button(RIGHT_BUTTON);
	}

	/**
	 * Specifies that the given button should be clicked once.
	 *
	 * @param button the mouse button to click.
	 * @return the created click info.
	 * @throws NullPointerException if {@code button} is {@code null}.
	 */
	@Nonnull
	public static MouseClickInfo button(@Nonnull MouseButton button) {
		return new MouseClickInfo(button);
	}

	private MouseClickInfo(@Nonnull MouseButton button) {
		this.button = Objects.requireNonNull(button);
		this.times = 1;
	}

	/**
	 * @return the button to click.
	 */
	@Nonnull
	public MouseButton button() {
		return button;
	}

	/**
	 * @return how many times the {@link #button() mouse button} should be clicked.
	 */
	public int times() {
		return times;
	}

	/**
	 * Specifies how many times the mouse button should be clicked.
	 *
	 * @param newTimes the specified number of times to click the mouse button.
	 * @return this object.
	 */
	public MouseClickInfo times(int newTimes) {
		times = newTimes;
		return this;
	}

	@Override
	@Nonnull
	public String toString() {
		return String.format("%s[button=%s, times=%d]", getClass().getName(), button.toString(), times);
	}
}
