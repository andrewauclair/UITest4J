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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for {@link ComponentMoveTask#moveComponent(java.awt.Component, Point)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentMoveTask_moveComponent_Test extends SwingRobotBasedTestCase {
	private TestWindow window;
	private Point location;
	private boolean moved;

	@Override
	protected void onSetUp() {
		moved = false;
		window = TestWindow.createNewWindow(getClass());
		robot.showWindow(window);
		location = new Point(80, 60);
		assertThat(location).isNotEqualTo(window.getLocationOnScreen());
	}

	@Test
	void should_Move_Component_To_Given_Location() {
		window.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
				moved = true;
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
		});
		ComponentMoveTask.moveComponent(window, location);
		robot.waitForIdle();
		assertAll(
				() -> assertTrue(moved),
				() -> assertThat(location.getX()).isEqualTo(window.getLocationOnScreen().getX())
		);
	}
}
