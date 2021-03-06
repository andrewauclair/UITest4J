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
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.data.BooleanProvider;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.driver.JTreeSetEditableTask.setEditable;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTreeEditableQuery#isEditable(JTree)}.
 *
 * @author Alex Ruiz
 */
class JTreeEditableQuery_isEditable_Test extends RobotBasedTestCase {
	private MyTree tree;

	private static Collection<Object[]> booleans() {
		return newArrayList(BooleanProvider.booleans());
	}

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		tree = window.tree;
	}

	@ParameterizedTest
	@MethodSource("booleans")
	void should_Indicate_If_JTree_Is_Editable(boolean editable) {
		setEditable(tree, editable);
		robot.waitForIdle();
		tree.startRecording();
		assertThat(JTreeEditableQuery.isEditable(tree)).isEqualTo(editable);
		tree.requireInvoked("isEditable");
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final MyTree tree = new MyTree();

		private MyWindow() {
			super(JTreeEditableQuery_isEditable_Test.class);
			addComponents(tree);
		}
	}

	private static class MyTree extends JTree {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		MyTree() {
			super(new DefaultMutableTreeNode("root"));
		}

		@Override
		public boolean isEditable() {
			if (recording) {
				methodInvocations.invoked("isEditable");
			}
			return super.isEditable();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
