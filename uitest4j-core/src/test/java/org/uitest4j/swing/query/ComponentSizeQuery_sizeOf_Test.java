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

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link ComponentSizeQuery#sizeOf(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentSizeQuery_sizeOf_Test extends SequentialEDTSafeTestCase {
	private static final Dimension SIZE = new Dimension(640, 480);

	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
		window.display();
	}

	@Override
	protected void onTearDown() {
		window.destroy();
	}

	@Test
	void should_Return_Size_Of_Component() {
		window.startRecording();
		assertThat(ComponentSizeQuery.sizeOf(window)).isEqualTo(SIZE);
		window.requireInvoked("getSize");
	}

	private static class MyWindow extends TestWindow {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(ComponentSizeQuery_sizeOf_Test.class);
			setMinimumSize(new Dimension(SIZE));
			setMaximumSize(new Dimension(SIZE));
		}

		@Override
		public Dimension getSize() {
			if (recording) {
				methodInvocations.invoked("getSize");
			}
			return super.getSize();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
