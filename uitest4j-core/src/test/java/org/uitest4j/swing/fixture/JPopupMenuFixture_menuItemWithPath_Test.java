/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.util.Platform.isOSX;

/**
 * Tests for {@link JPopupMenuFixture#menuItemWithPath(String...)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
// TODO(Alex): Test JMenuItemFinder instead.
public class JPopupMenuFixture_menuItemWithPath_Test extends RobotBasedTestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	private MyWindow window;
	private JPopupMenuFixture fixture;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew();
		robot.showWindow(window, new Dimension(200, 200));
		JTextComponentFixture textBox = new JTextComponentFixture(robot, "textField");
		textBox.click();
		fixture = textBox.showPopupMenu();
	}

	@Test
	public void should_Find_First_Level_JMenuItem_By_Path() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.fileMenu);
		JMenuItemFixture menuItem = fixture.menuItemWithPath("File");
		menuItem.click();
		recorder.clicked(LEFT_BUTTON).timesClicked(1);
	}

	@Test
	public void should_Find_Second_Level_JMenuItem_By_Path() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.openMenu);
		if (isOSX()) {
			fixture.menuItemWithPath("File").click();
		}
		JMenuItemFixture menuItem = fixture.menuItemWithPath("File", "Open");
		menuItem.click();
		recorder.clicked(LEFT_BUTTON).timesClicked(1);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JPopupMenu popupMenu = new JPopupMenu();
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openMenu = new JMenuItem("Open");

		private MyWindow() {
			super(JPopupMenuFixture_menuItemWithPath_Test.class);
			JTextField textField = new JTextField(5);
			textField.setName("textField");
			textField.setComponentPopupMenu(popupMenu);
			add(textField);
			popupMenu.add(fileMenu);
			fileMenu.add(openMenu);
		}
	}
}
