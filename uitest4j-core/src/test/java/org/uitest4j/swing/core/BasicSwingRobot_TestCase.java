/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.query.ComponentShowingQuery.isShowing;
import static org.uitest4j.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;
import static org.uitest4j.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;
import static org.uitest4j.swing.test.task.FrameShowTask.waitForShowing;

/**
 * Base case for tests for {@link BasicSwingRobot}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class BasicSwingRobot_TestCase extends EDTSafeTestCase {
	private BasicSwingRobot robot;
	private MyWindow window;

	@BeforeEach
	public final void setUp() {
		robot = (BasicSwingRobot) BasicSwingRobot.robotWithCurrentAwtHierarchy();
		window = MyWindow.createAndShow(checkNotNull(getClass()));
		beforeShowingWindow();
		robot.showWindow(window); // implicitly test 'showWindow(Window)'
		assertThat(isShowing(window)).isTrue();
		// TODO Taking this out for now because it doesn't work on the Ubuntu VM because the dialog has been positioned at this point
//    assertThat(locationOnScreen(window)).isEqualTo(new Point(100, 100));
	}

	void beforeShowingWindow() {
	}

	@AfterEach
	public final void tearDown() {
		try {
			window.destroy();
		}
		finally {
			robot.cleanUp();
		}
	}

	@RunsInEDT
	final void giveFocusToTextField() {
		giveFocusAndWaitTillIsFocused(window().textField());
	}

	@RunsInEDT
	final JPopupMenu addPopupMenuToTextField() {
		return createAndSetPopupMenu(window().textField(), "Luke", "Leia");
	}

	static class MyWindow extends TestWindow {
		private final JTextField textField = new JTextField(10);

		@RunsInEDT
		static @Nonnull
		MyWindow createAndShow(final @Nonnull Class<?> testClass) {
			MyWindow result = execute(() -> display(new MyWindow(testClass)));
			waitForShowing(result);
			return checkNotNull(result);
		}

		private MyWindow(@Nonnull Class<?> testClass) {
			super(testClass);
			addComponents(textField);
			setMinimumSize(new Dimension(100, 50));
		}

		@Nonnull
		JTextField textField() {
			return checkNotNull(textField);
		}
	}

	@Nonnull
	BasicSwingRobot robot() {
		return checkNotNull(robot);
	}

	@Nonnull
	MyWindow window() {
		return checkNotNull(window);
	}
}
