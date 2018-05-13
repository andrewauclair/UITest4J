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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.driver.JSliderValueQuery.valueOf;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JSliderSetValueTask#setValue(JSlider, int)}.
 *
 * @author Alex Ruiz
 */
class JSliderSetValueTask_setValue_Test extends RobotBasedTestCase {
	private JSlider slider;

	private static Collection<Object[]> values() {
		return newArrayList(new Object[][]{{8}, {10}, {28}, {68}, {80}});
	}

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		slider = window.slider;
	}

	@ParameterizedTest
	@MethodSource("values")
	void shouldReturnValueOfJSlider(int value) {
		JSliderSetValueTask.setValue(slider, value);
		robot.waitForIdle();
		assertThat(valueOf(slider)).isEqualTo(value);
	}

	private static class MyWindow extends TestWindow {
		final JSlider slider = new JSlider(6, 80);

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JSliderSetValueTask_setValue_Test.class);
			slider.setValue(6);
			addComponents(slider);
		}
	}
}
