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
package org.uitest4j.javafx.core;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.uitest4j.core.api.javafx.FXRobot;
import org.uitest4j.core.api.swing.SwingInputEventGenerator;
import org.uitest4j.javafx.JavaFX;
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.core.SwingRobotEventGenerator;
import org.uitest4j.swing.core.Settings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.BUTTON2_DOWN_MASK;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.core.MouseButton.RIGHT_BUTTON;
import static org.uitest4j.swing.util.Modifiers.keysFor;

/**
 * @author Andrew Auclair
 */
public class BasicFXRobot implements FXRobot {
	private static final int BUTTON_MASK = BUTTON1_DOWN_MASK | BUTTON2_DOWN_MASK | BUTTON3_DOWN_MASK;

	private Stage stage;

	private final Settings settings;
	private final FXRobotEventGenerator eventGenerator;

	public BasicFXRobot(Stage stage) {
		this.stage = stage;
		settings = new Settings();
		eventGenerator = new FXRobotEventGenerator(settings);
	}

	@Override
	public void click(@Nonnull Node node) {
		click(node, LEFT_BUTTON);
	}

	@Override
	public void rightClick(@Nonnull Node node) {
		click(node, RIGHT_BUTTON);
	}

	@Override
	public void click(@Nonnull Node node, @Nonnull MouseButton button) {
		click(node, button, 1);
	}

	@Override
	public void doubleClick(@Nonnull Node node) {
		click(node, LEFT_BUTTON, 2);
	}

	@Override
	public void click(@Nonnull Node node, @Nonnull MouseButton button, int times) {
		Point2D where = JavaFX.visibleCenterOf(node);
		click(node, where, button, times);
	}

	@Override
	public void click(@Nonnull Node node, @Nonnull Point2D where) {
		eventGenerator.pressMouse(node, where, LEFT_BUTTON.mask);
	}

	@Override
	public void click(@Nonnull Node node, @Nonnull Point2D where, @Nonnull MouseButton button, int times) {
		doClick(node, where, button, times);
	}

	@Override
	public void click(@Nonnull Point2D where, @Nonnull MouseButton button, int times) {
		doClick(null, where, button, times);
	}

	@Override
	public void pressAndReleaseKey(int keyCode, @Nonnull int... modifiers) {

	}

	@Override
	public void pressAndReleaseKeys(@Nonnull int... keyCodes) {

	}

	@Override
	public void pressKey(int keyCode) {

	}

	@Override
	public void pressKeyWhileRunning(int keyCode, @Nonnull Runnable runnable) {

	}

	@Override
	public void releaseKey(int keyCode) {
		eventGenerator.releaseKey(keyCode);
//		waitForIdle();
	}

	@Override
	public void pressModifiers(int modifierMask) {
		for (int modifierKey : keysFor(modifierMask)) {
			pressKey(modifierKey);
		}
	}

	@Override
	public void pressModifiersWhileRunning(int modifierMask, @Nonnull Runnable runnable) {
		pressModifiers(modifierMask);
		try {
			runnable.run();
		}
		finally {
			releaseModifiers(modifierMask);
		}
	}

	@Override
	public void releaseModifiers(int modifierMask) {
		// For consistency, release in the reverse order of press.
		int[] modifierKeys = keysFor(modifierMask);
		for (int i = modifierKeys.length - 1; i >= 0; i--) {
			releaseKey(modifierKeys[i]);
		}
	}

	private void doClick(@Nullable Node node, @Nonnull Point2D where, @Nonnull MouseButton button, int times) {
		int mask = button.mask;
		int modifierMask = mask & ~BUTTON_MASK;
		mask &= BUTTON_MASK;
		final int finalMask = mask;
		pressModifiersWhileRunning(modifierMask, () -> doClickWhileModifiersPressed(node, where, times, finalMask));
	}

	private void doClickWhileModifiersPressed(Node node, Point2D where, int times, int mask) {
		// From Abbot: Adjust the auto-delay to ensure we actually get a multiple click
		// In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
		int delayBetweenEvents = settings.delayBetweenEvents();
		if (shouldSetDelayBetweenEventsToZeroWhenClicking(times)) {
			settings.delayBetweenEvents(0);
		}
		if (node == null) {
			eventGenerator.pressMouse(where, mask);
			for (int i = times; i > 1; i--) {
				eventGenerator.releaseMouse(mask);
				eventGenerator.pressMouse(mask);
			}
		}
		else {
			eventGenerator.pressMouse(node, where, mask);
			for (int i = times; i > 1; i--) {
				eventGenerator.releaseMouse(mask);
				eventGenerator.pressMouse(mask);
			}
		}
		settings.delayBetweenEvents(delayBetweenEvents);
		eventGenerator.releaseMouse(mask);
	}

	private boolean shouldSetDelayBetweenEventsToZeroWhenClicking(int times) {
		return times > 1 /* FEST-137: && settings.delayBetweenEvents() * 2 > 200 */;
	}
}
