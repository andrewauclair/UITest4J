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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.ComponentLookupScope.DEFAULT;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for bug <a href="http://jira.codehaus.org/browse/FEST-139" target="_blank">FEST_139</a>.
 *
 * @author Woody Folsom
 * @author Alex Ruiz
 */
class FEST139_ComponentFinderNotUsingSwingRobotSettings_Test extends SwingRobotBasedTestCase {
	@Test
	void finder_should_use_settings_from_Robot() {
		MyWindow.createNew();
		MyWindow window = MyWindow.createNew();
		robot.showWindow(window);
		assertThat(robot.settings().componentLookupScope()).isEqualTo(DEFAULT);
		Component found = robot.finder().findByName("testLabel");
		assertThat(found).isSameAs(window.label);
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JLabel label = new JLabel("Test Label");

		private MyWindow() {
			super(FEST139_ComponentFinderNotUsingSwingRobotSettings_Test.class);
			label.setName("testLabel");
			addComponents(label);
		}
	}
}
