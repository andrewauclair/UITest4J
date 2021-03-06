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
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JComponentToolTipQuery#toolTipOf(javax.swing.JComponent)}.
 *
 * @author Alex Ruiz
 */
public class JComponentToolTipQuery_toolTipOf_Test extends RobotBasedTestCase {
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
	}

	@Test
	public void should_Return_ToolTip_From_Component() {
		assertThat(JComponentToolTipQuery.toolTipOf(window.button)).isEqualTo("A ToolTip");
	}

	private static class MyWindow extends TestWindow {
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JButton button = new JButton("Click Me");

		private MyWindow() {
			super(JComponentToolTipQuery_toolTipOf_Test.class);
			button.setToolTipText("A ToolTip");
			addComponents(button);
		}
	}
}
