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

import javax.annotation.Nonnull;

/**
 * @author Andrew Auclair
 */
public interface FXInputEventGenerator {
	/**
	 * Simulates a user pressing mouse buttons.
	 *
	 * @param buttons the buttons to press.
	 */
	void pressMouse(int buttons);

	/**
	 * Simulates a user pressing the given mouse buttons on the given JavaFX {@code Node}.
	 *
	 * @param node    the {@code Node} to click on.
	 * @param where   the given coordinates, relative to the given {@code Node}.
	 * @param buttons the mouse buttons to press.
	 */
	void pressMouse(@Nonnull Node node, @Nonnull Point2D where, int buttons);

	/**
	 * Simulates a user pressing the given mouse buttons on the given coordinates.
	 *
	 * @param where   the coordinates where to press the given mouse buttons.
	 * @param buttons the mouse buttons to press.
	 */
	void pressMouse(@Nonnull Point2D where, int buttons);

	/**
	 * Simulates a user moving the mouse pointer to the given coordinates relative to the given JavaFX
	 * {@code Node}.
	 *
	 * @param c the given {@code Node}.
	 * @param x X coordinate, relative to the given {@code Node}.
	 * @param y Y coordinate, relative to the given {@code Node}.
	 */
	void moveMouse(@Nonnull Node c, double x, double y);

	/**
	 * Simulates a user moving the mouse pointer to the given coordinates.
	 *
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	void moveMouse(double x, double y);

	/**
	 * Releases the given mouse buttons.
	 *
	 * @param buttons the mouse buttons to release.
	 */
	void releaseMouse(int buttons);

	/**
	 * Rotates the scroll wheel on wheel-equipped mice.
	 *
	 * @param amount number of "notches" to move the mouse wheel. Negative values indicate movement up/away from the user,
	 *               while positive values indicate movement down/towards the user.
	 */
	void rotateMouseWheel(int amount);

	/**
	 * Simulates a user pressing given key.
	 *
	 * @param keyCode the code of the key to press.
	 * @param keyChar the given character.
	 * @throws IllegalArgumentException if the key code is not valid.
	 * @see java.awt.event.KeyEvent
	 */
	void pressKey(int keyCode, char keyChar);

	/**
	 * Simulates a user releasing the given key.
	 *
	 * @param keyCode the code of the key to release.
	 * @see java.awt.event.KeyEvent
	 */
	void releaseKey(int keyCode);
}
