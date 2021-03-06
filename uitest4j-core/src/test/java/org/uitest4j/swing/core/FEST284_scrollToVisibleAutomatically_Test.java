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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.Box.createVerticalStrut;
import static javax.swing.BoxLayout.Y_AXIS;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-284" target="_blank">FEST_284</a>.
 *
 * @author Alex Ruiz
 * @author Juhos Csaba-Zsolt
 */
class FEST284_scrollToVisibleAutomatically_Test extends RobotBasedTestCase {
	private ClickRecorderManager clickRecorder = new ClickRecorderManager();

	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createAndShow();
	}

	@Test
	void should_Auto_Scroll_When_Clicking_JButton() {
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
		robot.click(window.button);
		recorder.clicked(LEFT_BUTTON).timesClicked(1);
	}

	private static class MyWindow extends TestWindow {
		final JButton button = new JButton("Click Me");

		static MyWindow createAndShow() {
			return execute(new GuiQuery<>() {
				@Override
				protected MyWindow executeInEDT() {
					MyWindow w = new MyWindow();
					display(w);
					w.setLocation(0, 0);
					w.setSize(new Dimension(800, 600));
					return w;
				}
			});
		}

		private MyWindow() {
			super(FEST284_scrollToVisibleAutomatically_Test.class);
			setLayout(new BorderLayout());
			add(buildNestedScrollPanes(32, 1000), CENTER);
		}

		Component buildNestedScrollPanes(int levelCount, int pixelCount) {
			// if no more levels to build, just return the button
			if (levelCount == 0) {
				return button;
			}
			return scrollPaneWith(box(levelCount, pixelCount));
		}

		private Box box(int levelCount, int pixelCount) {
			Box box = new Box(Y_AXIS);
			box.add(createVerticalStrut(pixelCount));
			// create the nested component recursively
			box.add(buildNestedScrollPanes(levelCount - 1, pixelCount));
			return box;
		}

		private Component scrollPaneWith(Box box) {
			JScrollPane p = new JScrollPane();
			p.setViewportView(box);
			return p;
		}
	}
}
