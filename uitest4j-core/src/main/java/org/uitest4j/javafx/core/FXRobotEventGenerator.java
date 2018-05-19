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
import org.uitest4j.core.api.javafx.FXInputEventGenerator;
import org.uitest4j.javafx.JavaFX;
import org.uitest4j.swing.core.Settings;
import org.uitest4j.swing.util.RobotFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;
import static org.uitest4j.swing.exception.UnexpectedException.unexpected;
import static org.uitest4j.swing.timing.Pause.pause;
import static org.uitest4j.swing.util.Platform.isOSX;
import static org.uitest4j.swing.util.Platform.isWindows;

/**
 * @author Andrew Auclair
 */
public class FXRobotEventGenerator implements FXInputEventGenerator {
	private static final int KEY_INPUT_DELAY = 200;

	private final Robot robot;
	private final Settings settings;

	FXRobotEventGenerator() {
		this(new Settings());
	}

	FXRobotEventGenerator(@Nonnull Settings settings) {
		this(new RobotFactory(), settings);
	}

	FXRobotEventGenerator(@Nonnull RobotFactory robotFactory, @Nonnull Settings settings) {
		try {
			robot = robotFactory.newRobotInLeftScreen();
			if (isWindows() || isOSX()) {
				pause(500);
			}
		}
		catch (AWTException e) {
			throw unexpected(e);
		}
		this.settings = settings;
		settings.attachTo(robot);
	}

	@Nonnull
	Robot robot() {
		return robot;
	}

	@Override
	public void pressMouse(int buttons) {
		robot.mousePress(buttons);
	}

	@Override
	public void pressMouse(@Nonnull Node node, @Nonnull Point2D where, int buttons) {
		Point2D p = Objects.requireNonNull(executeFX(() -> JavaFX.localToScreen(node, where.getX(), where.getY())));
		pressMouse(p, buttons);
	}

	@Override
	public void pressMouse(@Nonnull Point2D where, int buttons) {
		moveMouse(where.getX(), where.getY());
		pressMouse(buttons);
	}

	@Override
	public void moveMouse(@Nonnull Node node, double x, double y) {
		Point2D p = Objects.requireNonNull(executeFX(() -> JavaFX.localToScreen(node, x, y)));
		moveMouse(p.getX(), p.getX());
	}

	@Override
	public void moveMouse(double x, double y) {
		robot.mouseMove((int) x, (int) y);
	}

	@Override
	public void releaseMouse(int buttons) {
		robot.mouseRelease(buttons);
	}

	@Override
	public void rotateMouseWheel(int amount) {
		robot.mouseWheel(amount);
	}

	@Override
	public void pressKey(int keyCode, char keyChar) {
		try {
			robot.keyPress(keyCode);
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format("Invalid key code '%d'", keyCode));
		}
	}

	@Override
	public void releaseKey(int keyCode) {
		robot.keyRelease(keyCode);
		if (!isOSX()) {
			return;
		}
		int delayBetweenEvents = settings.delayBetweenEvents();
		if (KEY_INPUT_DELAY > delayBetweenEvents) {
			pause(KEY_INPUT_DELAY - delayBetweenEvents);
		}
	}
}
