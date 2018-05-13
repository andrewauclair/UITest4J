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
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.query.ComponentParentQuery.parentOf;

/**
 * Tests for {@link WindowAncestorFinder#windowAncestorOf(java.awt.Component)}.
 *
 * @author Yvonne Wang
 */
public class WindowAncestorFinder_windowAncestorOf_Test extends EDTSafeTestCase {
	private MyWindow frame;

	@BeforeEach
	void setUp() {
		frame = MyWindow.createNew();
	}

	@AfterEach
	void tearDown() {
		frame.destroy();
	}

	@Test
	void should_Find_Window_Ancestor() {
		Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.button);
		assertThat(ancestor).isSameAs(frame);
	}

	@Test
	void should_Return_Null_If_Component_Is_Null() {
		Window ancestor = WindowAncestorFinder.windowAncestorOf(new Component() {
		});
		assertThat(ancestor).isSameAs(null);
	}

	@Test
	void should_Return_Window_As_Its_Own_Ancestor() {
		Window ancestor = WindowAncestorFinder.windowAncestorOf(frame);
		assertThat(ancestor).isSameAs(frame);
	}

	@Test
	void should_Return_Invoker_As_Ancestor_Of_MenuElement() {
		Robot robot = null;
		try {
			robot = BasicRobot.robotWithCurrentAwtHierarchy();
			robot.showWindow(frame);
			robot.showPopupMenu(frame.textField);
			Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.popupMenu);
			assertThat(ancestor).isSameAs(frame);
		}
		finally {
			if (robot != null) {
				robot.cleanUp();
			}
		}
	}

	@Test
	void should_Return_Parent_As_Ancestor_If_Component_Is_MenuElement_And_Invoker_Is_Null() {
		Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.popupMenu);
		assertThat(ancestor).isSameAs(parentOf(frame.popupMenu));
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JButton button = new JButton("Click Me");
		final JTextField textField = new JTextField(20);
		final JPopupMenu popupMenu = new JPopupMenu();

		private MyWindow() {
			super(WindowAncestorFinder_windowAncestorOf_Test.class);
			add(button);
			add(textField);
			textField.setComponentPopupMenu(popupMenu);
			popupMenu.add(new JMenuItem("Frodo"));
		}
	}
}
