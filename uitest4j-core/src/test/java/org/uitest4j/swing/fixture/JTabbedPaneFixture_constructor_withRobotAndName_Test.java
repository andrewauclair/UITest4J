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
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTabbedPaneFixture#JTabbedPaneFixture(org.uitest4j.swing.core.Robot, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
	}

	@Test
	void should_Lookup_Showing_JTabbedPane_By_Name() {
		robot.showWindow(window);
		JTabbedPaneFixture fixture = new JTabbedPaneFixture(robot, "tabbedPane");
		assertThat(fixture.robot()).isSameAs(robot);
		assertThat(fixture.target()).isSameAs(window.tabbedPane);
	}

	@Test
	void should_Throw_Error_If_JTabbedPane_With_Matching_Name_Is_Not_Showing() {
		assertThrows(ComponentLookupException.class, () -> new JTabbedPaneFixture(robot, "tabbedPane"));
	}

	@Test
	void should_Throw_Error_If_A_JTabbedPane_With_Matching_Name_Is_Not_Found() {
		assertThrows(ComponentLookupException.class, () -> new JTabbedPaneFixture(robot, "other"));
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTabbedPane tabbedPane = new JTabbedPane();

		private MyWindow() {
			super(JTabbedPaneFixture_constructor_withRobotAndName_Test.class);
			tabbedPane.setPreferredSize(new Dimension(100, 50));
			tabbedPane.setName("tabbedPane");
			tabbedPane.addTab("One", new JPanel());
			addComponents(tabbedPane);
		}
	}
}
