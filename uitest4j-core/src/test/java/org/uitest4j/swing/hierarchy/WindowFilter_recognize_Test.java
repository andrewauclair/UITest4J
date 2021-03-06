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

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JButtons.button;
import static org.uitest4j.swing.test.builder.JDialogs.dialog;

/**
 * Tests for {@link WindowFilter#recognize(Component)}.
 *
 * @author Alex Ruiz
 */
class WindowFilter_recognize_Test extends WindowFilter_TestCase {
	@Test
	void should_Recognize_Component() {
		Component c = button().createNew();
		addToIgnoredMap(c);
		addToImplicitlyIgnoredMap(c);
		recognize(filter, c);
		assertThatNoComponentsAreIgnored();
		assertThatNoComponentsAreImplicitlyIgnored();
	}

	@Test
	void should_Recognize_Children_Of_Shared_Invisible_Frame() {
		JDialog dialog = dialog().createNew();
		addToIgnoredMap(dialog);
		addToImplicitlyIgnoredMap(dialog);
		recognize(filter, dialog.getOwner());
		assertThatNoComponentsAreIgnored();
		assertThatNoComponentsAreImplicitlyIgnored();
	}

	@RunsInEDT
	private static void recognize(final WindowFilter filter, final Component c) {
		execute(() -> filter.recognize(c));
	}
}
