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

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JOptionPaneOptionsQuery#optionsOf(JOptionPane)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JOptionPaneOptionsQuery_optionsOf_Test extends RobotBasedTestCase {
	private MyOptionPane optionPane;

	@Override
	protected void onSetUp() {
		optionPane = MyOptionPane.createNew();
	}

	@Test
	public void should_Return_Message_Of_JOptionPane() {
		optionPane.startRecording();
		assertThat(JOptionPaneOptionsQuery.optionsOf(optionPane)).containsOnly(optionPane.option);
		optionPane.requireInvoked("getOptions");
	}

	private static class MyOptionPane extends JOptionPane {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		final JButton option = new JButton("Hello");

		@RunsInEDT
		static MyOptionPane createNew() {
			return execute(MyOptionPane::new);
		}

		private MyOptionPane() {
			super("Hello World", INFORMATION_MESSAGE);
			setOptions(array(option));
		}

		@Override
		public Object[] getOptions() {
			if (recording) {
				methodInvocations.invoked("getOptions");
			}
			return super.getOptions();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
