/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JMenuPopupMenuQuery#popupMenuOf(JMenu)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JMenuPopupMenuQuery_popupMenuOf_Test extends RobotBasedTestCase {
	private MyMenu menu;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		menu = window.menu;
	}

	@Test
	public void should_Return_JPopupMenu_In_JMenu() {
		JPopupMenu expected = popupMenuOf(menu);
		menu.startRecording();
		JPopupMenu actual = JMenuPopupMenuQuery.popupMenuOf(menu);
		assertThat(actual).isNotNull().isSameAs(expected);
		menu.requireInvoked("getPopupMenu");
	}

	@RunsInEDT
	private static JPopupMenu popupMenuOf(final MyMenu menu) {
		return execute(menu::getPopupMenu);
	}

	private static class MyWindow extends TestWindow {
		final MyMenu menu = new MyMenu("Hello");

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JMenuPopupMenuQuery_popupMenuOf_Test.class);
			JMenuBar menuBar = new JMenuBar();
			menuBar.add(menu);
			setJMenuBar(menuBar);
		}
	}

	private static class MyMenu extends JMenu {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		MyMenu(String text) {
			super(text);
		}

		@Override
		public JPopupMenu getPopupMenu() {
			if (recording) {
				methodInvocations.invoked("getPopupMenu");
			}
			return super.getPopupMenu();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
