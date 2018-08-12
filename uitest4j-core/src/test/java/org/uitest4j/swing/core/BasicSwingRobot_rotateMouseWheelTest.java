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
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link BasicSwingRobot#rotateMouseWheel(java.awt.Component, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicSwingRobot_rotateMouseWheelTest extends BasicSwingRobot_TestCase {
	private JList list;
	private JScrollPane scrollPane;

	@RunsInEDT
	@Override
	void beforeShowingWindow() {
		execute(() -> {
			list = new JList(array("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"));
			scrollPane = new JScrollPane(list);
			scrollPane.setPreferredSize(new Dimension(300, 100));
			window().add(scrollPane);
		});
	}

	@Test
	void should_Rotate_Mouse_Wheel() {
		assertThat(firstVisibleIndexOf(list)).isEqualTo(0);
		MouseWheelRecorder recorder = MouseWheelRecorder.attachTo(scrollPane);
		int amount = 50;
		robot().rotateMouseWheel(list, amount);
		assertThat(recorder.wheelRotation()).isEqualTo(amount);
		assertThat(firstVisibleIndexOf(list)).isGreaterThan(0);
	}

	@RunsInEDT
	private static int firstVisibleIndexOf(final JList list) {
		Integer result = execute(list::getFirstVisibleIndex);
		return Objects.requireNonNull(result);
	}

	private static class MouseWheelRecorder implements MouseWheelListener {
		private int wheelRotation;

		static @Nonnull
		MouseWheelRecorder attachTo(@Nonnull Component c) {
			MouseWheelRecorder recorder = new MouseWheelRecorder();
			c.addMouseWheelListener(recorder);
			return recorder;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			wheelRotation += e.getWheelRotation();
		}

		int wheelRotation() {
			return wheelRotation;
		}
	}
}
