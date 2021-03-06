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
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.finder.WindowFinder.findFrame;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=157" target="_blank">issue 157</a>.
 *
 * @author Andriy Tsykholyas
 * @author Yvonne Wang
 */
public class Bug157_MacOsxMenuBar_Test extends RobotBasedTestCase {
	private JMenuItemFixture menuItemFixture;

	@Override
	protected void onSetUp() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		robot.showWindow(MyWindow.createNew());
		FrameFixture frameFixture = findFrame("myWindow").withTimeout(2000).using(robot);
		menuItemFixture = frameFixture.menuItem("menuItem");
	}

	@Test
	public void should_Select_Menu() {
		final boolean[] selected = new boolean[1];
		JMenuItem menu = menuItemFixture.target();
		menu.addActionListener(event -> selected[0] = true);
		menuItemFixture.click();
		assertThat(selected[0]).isTrue();
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(Bug157_MacOsxMenuBar_Test.class);
			setName("myWindow");
			setJMenuBar(menuBar(menu(menuItem())));
			setPreferredSize(new Dimension(200, 100));
		}

		private JMenuItem menuItem() {
			JMenuItem menuItem = new JMenuItem("Menu Item");
			menuItem.setName("menuItem");
			return menuItem;
		}

		private JMenu menu(JMenuItem menuItem) {
			JMenu menu = new JMenu("Menu");
			menu.setName("menu");
			menu.add(menuItem);
			return menu;
		}

		private JMenuBar menuBar(JMenu menu) {
			JMenuBar menuBar = new JMenuBar();
			menuBar.add(menu);
			return menuBar;
		}
	}
}
