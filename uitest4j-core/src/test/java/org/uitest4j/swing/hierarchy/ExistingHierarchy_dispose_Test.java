/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.hierarchy;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link ExistingHierarchy#dispose(java.awt.Window)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ExistingHierarchy_dispose_Test extends SequentialEDTSafeTestCase {
	private MyWindow window;
	private ExistingHierarchy hierarchy;

	@Override
	protected void onSetUp() {
		hierarchy = new ExistingHierarchy();
		window = MyWindow.createAndShow(getClass());
	}

	@Override
	protected void onTearDown() {
		window.destroy();
	}

	@Test
	void should_Dispose_Window() {
		window.startRecording();
		hierarchy.dispose(window);
		window.requireInvoked("dispose");
	}

	private static class MyWindow extends TestWindow {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		@RunsInEDT
		static MyWindow createAndShow(final Class<?> testClass) {
			return execute(() -> display(new MyWindow(testClass)));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
		}

		@Override
		public void dispose() {
			if (recording) {
				methodInvocations.invoked("dispose");
			}
			super.dispose();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
