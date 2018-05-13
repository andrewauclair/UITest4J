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
import org.uitest4j.swing.test.swing.TestDialog;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JButtons.button;

/**
 * Tests for {@link WindowFilter#isIgnored(Component)}.
 *
 * @author Alex Ruiz
 */
class WindowFilter_isIgnored_Test extends WindowFilter_TestCase {
	@Test
	void should_Return_True_If_Component_Is_Ignored() {
		Component c = button().createNew();
		addToIgnoredMap(c);
		assertThat(isComponentIgnored(filter, c)).isTrue();
	}

	@Test
	void should_Return_True_If_Window_Parent_Is_Ignored() {
		MyWindow window = MyWindow.createNew();
		Component c = window.button;
		addToIgnoredMap(window);
		assertThat(isComponentIgnored(filter, c)).isTrue();
	}

	@Test
	void should_Return_True_If_Parent_Of_Window_Is_Ignored() {
		TestWindow window = TestWindow.createNewWindow(getClass());
		TestDialog dialog = TestDialog.createNewDialog(window);
		addToIgnoredMap(window);
		assertThat(isComponentIgnored(filter, dialog)).isTrue();
	}

	@Test
	void should_Return_False_If_Given_Component_Is_Null() {
		assertThat(isComponentIgnored(filter, null)).isFalse();
	}

	@RunsInEDT
	private static boolean isComponentIgnored(final WindowFilter filter, final Component c) {
		return execute(() -> filter.isIgnored(c));
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JButton button = new JButton("Press Me");

		private MyWindow() {
			super(WindowFilter_TestCase.class);
			addComponents(button);
		}
	}
}
