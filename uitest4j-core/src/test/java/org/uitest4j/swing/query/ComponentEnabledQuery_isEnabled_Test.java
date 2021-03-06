/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.query;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.data.BooleanProvider;
import org.uitest4j.swing.test.swing.TestWindow;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.ComponentSetEnabledTask.setEnabled;

/**
 * Tests for {@link ComponentEnabledQuery#isEnabled(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentEnabledQuery_isEnabled_Test extends RobotBasedTestCase {
	private MyWindow window;

	private static Collection<Object[]> booleans() {
		return newArrayList(BooleanProvider.booleans());
	}

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
	}

	@ParameterizedTest
	@MethodSource("booleans")
	void should_Indicate_If_Component_Is_Enabled_Or_Not(boolean enabled) {
		setEnabled(window, enabled);
		robot.waitForIdle();
		window.startRecording();
		assertThat(ComponentEnabledQuery.isEnabled(window)).isEqualTo(enabled);
		window.requireInvoked("isEnabled");
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		private MyWindow() {
			super(ComponentEnabledQuery_isEnabled_Test.class);
		}

		@Override
		public boolean isEnabled() {
			if (recording) {
				methodInvocations.invoked("isEnabled");
			}
			return super.isEnabled();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
