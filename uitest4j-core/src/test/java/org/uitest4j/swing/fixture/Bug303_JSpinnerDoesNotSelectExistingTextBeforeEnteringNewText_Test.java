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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=303">Bug 303</a>.
 *
 * @author Alex Ruiz
 */
public class Bug303_JSpinnerDoesNotSelectExistingTextBeforeEnteringNewText_Test extends RobotBasedTestCase {
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		window = new FrameFixture(robot, MyWindow.createNew());
		window.show();
	}

	@Override
	protected void onTearDown() {
		window.cleanUp();
	}

	@Test
	void should_Select_Existing_Text_Before_Entering_New_Ext() {
		window.spinner("spinner1").focus();
		window.spinner("spinner2").enterTextAndCommit("Gandalf").requireValue("Gandalf");
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JSpinner spinner1 = new JSpinner(new SpinnerListModel(array("Anakin", "Leia", "Han")));
		final JSpinner spinner2 = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));

		private MyWindow() {
			super(Bug303_JSpinnerDoesNotSelectExistingTextBeforeEnteringNewText_Test.class);
			spinner1.setName("spinner1");
			spinner2.setName("spinner2");
			addComponents(spinner1, spinner2);
		}
	}
}
