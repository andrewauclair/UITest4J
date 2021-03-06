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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link ComponentShowingQuery#isShowing(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentShowingQuery_isShowing_Test extends SequentialEDTSafeTestCase {
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
	}

	@Override
	protected void onTearDown() {
		window.destroy();
	}

	@Test
	void should_Return_False_Component_Is_Not_Showing() {
		window.startRecording();
		assertThat(ComponentShowingQuery.isShowing(window)).isFalse();
		window.requireInvoked("isShowing");
	}

	@Test
	void should_Return_True_If_Component_Is_Showing() {
		window.display();
		window.startRecording();
		assertThat(ComponentShowingQuery.isShowing(window)).isTrue();
		window.requireInvoked("isShowing");
	}

	private static class MyWindow extends TestWindow {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(ComponentShowingQuery_isShowing_Test.class);
		}

		@Override
		public boolean isShowing() {
			if (recording) {
				methodInvocations.invoked("isShowing");
			}
			return super.isShowing();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
