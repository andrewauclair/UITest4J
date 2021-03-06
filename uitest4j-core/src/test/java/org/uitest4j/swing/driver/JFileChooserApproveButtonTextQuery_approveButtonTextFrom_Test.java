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

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JFileChooserApproveButtonTextQuery#approveButtonTextFrom(JFileChooser)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JFileChooserApproveButtonTextQuery_approveButtonTextFrom_Test extends RobotBasedTestCase {
	private MyFileChooser fileChooser;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		fileChooser = window.fileChooser;
	}

	@Test
	void should_Return_Text_For_Approve_Button_From_JFileChooser() {
		fileChooser.startRecording();
		String approveButtonText = JFileChooserApproveButtonTextQuery.approveButtonTextFrom(fileChooser);
		assertThat(approveButtonText).isNotEmpty();
		fileChooser.requireInvoked("getApproveButtonText");
	}

	@Test
	void should_Return_Text_For_Approve_Button_From_UI_If_JFileChooser_Does_Not_Have_It() {
		fileChooser.shouldReturnNullAsApproveButtonText(true);
		fileChooser.startRecording();
		String approveButtonText = JFileChooserApproveButtonTextQuery.approveButtonTextFrom(fileChooser);
		assertThat(approveButtonText).isNotEmpty();
		fileChooser.requireInvoked("getApproveButtonText");
	}

	private static class MyWindow extends TestWindow {
		final MyFileChooser fileChooser = new MyFileChooser();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JFileChooserApproveButtonTextQuery_approveButtonTextFrom_Test.class);
			addComponents(fileChooser);
		}
	}

	private static class MyFileChooser extends JFileChooser {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		private boolean returnNullAsApproveButtonText;

		void startRecording() {
			recording = true;
		}

		void shouldReturnNullAsApproveButtonText(boolean b) {
			returnNullAsApproveButtonText = b;
		}

		@Override
		public String getApproveButtonText() {
			if (recording) {
				methodInvocations.invoked("getApproveButtonText");
			}
			if (returnNullAsApproveButtonText) {
				return null;
			}
			return super.getApproveButtonText();
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
