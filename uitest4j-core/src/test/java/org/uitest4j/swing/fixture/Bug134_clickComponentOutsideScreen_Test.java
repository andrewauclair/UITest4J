/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=134">Bug 134</a>.
 *
 * @author Alex Ruiz
 */
public class Bug134_clickComponentOutsideScreen_Test extends RobotBasedTestCase {
	private FrameFixture fixture;

	@Override
	protected final void onSetUp() {
		MyWindow window = MyWindow.createNew();
		fixture = new FrameFixture(robot, window);
		fixture.show();
	}

	@Test
	void should_Throw_Error_When_Clicking_Button_Outside_Screen() {
		moveWindowOutOfScreen();
		ExpectedException.assertContainsMessage(ActionFailedException.class, () -> fixture.button().click(), "The component to click is out of the boundaries of the screen");
	}

	private void moveWindowOutOfScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Rectangle screen = new Rectangle();

		GraphicsDevice[] gdArray = ge.getScreenDevices();

		for (GraphicsDevice gd : gdArray) {
			GraphicsConfiguration[] gcArray = gd.getConfigurations();

			for (GraphicsConfiguration aGcArray : gcArray) {
				screen = screen.union(aGcArray.getBounds());
			}
		}

		int x = screen.width;
		int y = screen.height / 2;
		fixture.moveTo(new Point(x, y));
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTextField textField = new JTextField(20);
		final JButton button = new JButton("Click Me");

		private MyWindow() {
			super(Bug134_clickComponentOutsideScreen_Test.class);
			addComponents(textField, button);
		}
	}
}
