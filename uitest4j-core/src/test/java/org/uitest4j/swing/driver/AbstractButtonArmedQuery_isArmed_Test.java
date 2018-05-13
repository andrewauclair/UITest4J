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
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.data.BooleanProvider;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.task.AbstractButtonSetArmedTask.setArmed;

/**
 * Tests for {@link AbstractButtonArmedQuery#isArmed(javax.swing.AbstractButton)}.
 *
 * @author Christian Rösch
 */
public class AbstractButtonArmedQuery_isArmed_Test extends RobotBasedTestCase {
	private JCheckBox checkBox;

	@Nonnull
	public static Collection<Object[]> booleans() {
		return newArrayList(BooleanProvider.booleans());
	}

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		checkBox = window.checkBox;
	}

	@ParameterizedTest
	@MethodSource("booleans")
	void should_Indicate_If_AbstractButton_Is_Armed(boolean armed) {
		setArmed(checkBox, armed);
		robot.waitForIdle();
		boolean isArmed = AbstractButtonArmedQuery.isArmed(checkBox);
		assertThat(isArmed).isEqualTo(armed);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JCheckBox checkBox = new JCheckBox("A Button");

		private MyWindow() {
			super(AbstractButtonArmedQuery_isArmed_Test.class);
			addComponents(checkBox);
		}
	}
}
