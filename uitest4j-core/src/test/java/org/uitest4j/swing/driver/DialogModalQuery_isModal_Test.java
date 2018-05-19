/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.data.BooleanProvider;
import org.uitest4j.swing.test.swing.TestDialog;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.DialogSetModalTask.makeModal;

/**
 * Tests for {@link DialogModalQuery#isModal(java.awt.Dialog)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class DialogModalQuery_isModal_Test extends SwingRobotBasedTestCase {
	private MyDialog dialog;

	private static Collection<Object[]> booleans() {
		return newArrayList(BooleanProvider.booleans());
	}

	@Override
	protected void onSetUp() {
		dialog = MyDialog.createNew();
		dialog.setName("TestDialog");
	}

	@ParameterizedTest
	@MethodSource("booleans")
	void should_Indicate_If_Dialog_Is_Modal(boolean modal) {
		makeModal(dialog, modal);
		robot.waitForIdle();
		dialog.startRecording();
		assertThat(DialogModalQuery.isModal(dialog)).isEqualTo(modal);
		dialog.requireInvoked("isModal");
	}

	@Test
	void driver_requireModal_should_not_throw_exception_when_dialog_is_modal() {
		makeModal(dialog, true);
		robot.waitForIdle();
		dialog.startRecording();
		DialogDriver driver = new DialogDriver(robot);
		driver.requireModal(dialog);
		dialog.requireInvoked("isModal");
	}

	@Test
	void driver_requireModal_should_throw_exception_when_dialog_is_not_modal() {
		makeModal(dialog, false);
		robot.waitForIdle();
		dialog.startRecording();
		DialogDriver driver = new DialogDriver(robot);
		ExpectedException.assertOpenTest4jError(() -> driver.requireModal(dialog), "Expected 'TestDialog' to be modal");
		dialog.requireInvoked("isModal");
	}

	private static class MyDialog extends TestDialog {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		@RunsInEDT
		static MyDialog createNew() {
			return execute(MyDialog::new);
		}

		private MyDialog() {
			super(TestWindow.createNewWindow(DialogModalQuery_isModal_Test.class));
			setPreferredSize(new Dimension(200, 100));
		}

		void startRecording() {
			recording = true;
		}

		@Override
		public boolean isModal() {
			if (recording) {
				methodInvocations.invoked("isModal");
			}
			return super.isModal();
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
