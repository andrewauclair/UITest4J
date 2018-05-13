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
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link MenuElementComponentQuery#componentIn(javax.swing.MenuElement)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class MenuElementComponentQuery_componentIn_Test extends RobotBasedTestCase {
	private MyMenu menu;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		menu = window.menu;
	}

	@Test
	public void should_Return_Component_Of_MenuElement() {
		menu.startRecording();
		assertThat(MenuElementComponentQuery.componentIn(menu)).isSameAs(menu);
		menu.requireInvoked("getComponent");
	}

	private static class MyWindow extends TestWindow {
		final MyMenu menu = new MyMenu();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(MenuElementComponentQuery_componentIn_Test.class);
			setJMenuBar(new JMenuBar());
			getJMenuBar().add(menu);
			setPreferredSize(new Dimension(80, 60));
		}
	}

	private static class MyMenu extends JMenu {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		MyMenu() {
			super("Menu");
		}

		@Override
		public Component getComponent() {
			if (recording) {
				methodInvocations.invoked("getComponent");
			}
			return super.getComponent();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
