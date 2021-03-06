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
import static org.uitest4j.swing.test.builder.JDialogs.dialog;

/**
 * Tests for {@link WindowFilter#ignore(Component)}.
 *
 * @author Alex Ruiz
 */
class WindowFilter_ignore_Test extends WindowFilter_TestCase {
	@Test
	void should_Ignore_Component() {
		Component c = button().createNew();
		addToImplicitlyIgnoredMap(c);
		ignore(filter, c);
		assertThat(allIgnored()).containsOnly(c);
		assertThatNoComponentsAreImplicitlyIgnored();
	}

	@Test
	void should_Ignore_Owned_Windows() {
		TestWindow window = TestWindow.createNewWindow(getClass());
		TestDialog dialog = TestDialog.createNewDialog(window);
		addToImplicitlyIgnoredMap(window, dialog);
		ignore(filter, window);
		assertThat(allIgnored()).containsOnly(window, dialog);
		assertThatNoComponentsAreImplicitlyIgnored();
	}

	@Test
	void should_Ignore_Children_Of_Shared_Invisible_Frame() {
		JDialog dialog = dialog().createNew();
		addToImplicitlyIgnoredMap(dialog);
		ignore(filter, dialog.getOwner());
		assertThat(allIgnored()).containsOnly(dialog);
		assertThatNoComponentsAreImplicitlyIgnored();
	}

	@RunsInEDT
	private static void ignore(final WindowFilter filter, final Component c) {
		execute(() -> filter.ignore(c));
	}
}
