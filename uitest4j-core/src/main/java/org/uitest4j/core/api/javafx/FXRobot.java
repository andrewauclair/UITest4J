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
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.core.api.javafx;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.swing.core.MouseButton;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * @author Andrew Auclair
 */
public interface FXRobot {
	/**
	 * Simulates a user clicking once the given JavaFX {@code Node} using the left mouse button.
	 *
	 * @param node the {@code Node} to click on.
	 * @throws ActionFailedException if the {@code Node} to click is out of the
	 *                                                            boundaries of the screen.
	 */
	void click(@Nonnull Node node);

	/**
	 * Simulates a user clicking the given mouse button, the given times on the given JavaFX {@code Node}.
	 *
	 * @param node      the {@code Node} to click on.
	 * @param button the mouse button to click.
	 * @param times  the number of times to click the given mouse button.
	 * @throws ActionFailedException if the {@code Node} to click is out of the
	 *                                                            boundaries of the screen.
	 */
	void click(@Nonnull Node node, @Nonnull MouseButton button, int times);

	/**
	 * Simulates a user clicking at the given position on the given JavaFX {@code Node}.
	 *
	 * @param node     the {@code Node} to click on.
	 * @param where the given coordinates, relative to the given {@code Node}.
	 * @throws ActionFailedException if the {@code Node} to click is out of the
	 *                                                            boundaries of the screen.
	 */
	void click(@Nonnull Node node, @Nonnull Point2D where);

	/**
	 * Simulates a user clicking the given mouse button, the given times at the given position on the given JavaFX
	 * {@code Node}.
	 *
	 * @param c      the {@code Node} to click on.
	 * @param where  the given coordinates, relative to the given {@code Node}.
	 * @param button the mouse button to click.
	 * @param times  the number of times to click the given mouse button.
	 * @throws ActionFailedException if the {@code Node} to click is out of the
	 *                                                            boundaries of the screen.
	 */
	void click(@Nonnull Node node, @Nonnull Point2D where, @Nonnull MouseButton button, int times);

	/**
	 * Simulates a user clicking the given mouse button, the given times at the given absolute coordinates.
	 *
	 * @param where  the coordinates where to click.
	 * @param button the mouse button to click.
	 * @param times  the number of times to click the given mouse button.
	 */
	void click(@Nonnull Point2D where, @Nonnull MouseButton button, int times);

	/**
	 * Type the given key code with the given modifiers. Modifiers is a mask from the available
	 * {@code java.awt.event.InputEvent} masks.
	 *
	 * @param keyCode   the code of the key to press.
	 * @param modifiers the given modifiers.
	 * @throws IllegalArgumentException if the given code is not a valid key code.
	 */
	void pressAndReleaseKey(int keyCode, @Nonnull int... modifiers);

	/**
	 * Simulates a user pressing and releasing the given keys. This method does not affect the current focus.
	 *
	 * @param keyCodes one or more codes of the keys to press.
	 * @throws IllegalArgumentException if any of the given codes is not a valid key code.
	 * @see java.awt.event.KeyEvent
	 */
	void pressAndReleaseKeys(@Nonnull int... keyCodes);

	/**
	 * Simulates a user pressing given key. This method does not affect the current focus.
	 *
	 * @param keyCode the code of the key to press.
	 * @throws IllegalArgumentException if the given code is not a valid key code.
	 * @see java.awt.event.KeyEvent
	 * @see #pressKeyWhileRunning(int, Runnable)
	 */
	void pressKey(int keyCode);

	/**
	 * Simulates a user pressing given key, running the given runnable and releasing the key again. This method does not
	 * affect the current focus.
	 *
	 * @param keyCode the code of the key to press.
	 * @throws IllegalArgumentException if the given code is not a valid key code.
	 * @see java.awt.event.KeyEvent
	 * @see #pressKey(int)
	 */
	void pressKeyWhileRunning(int keyCode, @Nonnull Runnable runnable);

	/**
	 * Simulates a user releasing the given key. This method does not affect the current focus.
	 *
	 * @param keyCode the code of the key to release.
	 * @throws IllegalArgumentException if the given code is not a valid key code.
	 * @see java.awt.event.KeyEvent
	 */
	void releaseKey(int keyCode);

	/**
	 * Presses the appropriate modifiers corresponding to the given mask. Use mask values from
	 * {@code java.awt.event.InputEvent}.
	 *
	 * @param modifierMask the given mask.
	 * @see #pressModifiersWhileRunning(int, Runnable)
	 * @see java.awt.event.InputEvent
	 */
	void pressModifiers(int modifierMask);

	/**
	 * Presses the appropriate modifiers corresponding to the given mask. Use mask values from
	 * {@code java.awt.event.InputEvent}. Runs the given runnable and then releases the modifiers again.
	 *
	 * @param modifierMask the given mask.
	 * @param runnable     the runnable being run while pressing the given button
	 * @see #pressModifiers(int)
	 * @see java.awt.event.InputEvent
	 */
	void pressModifiersWhileRunning(int modifierMask, @Nonnull Runnable runnable);

	/**
	 * Releases the appropriate modifiers corresponding to the given mask. Use mask values from
	 * {@code java.awt.event.InputEvent}.
	 *
	 * @param modifierMask the given mask.
	 * @see java.awt.event.InputEvent
	 */
	void releaseModifiers(int modifierMask);
}
