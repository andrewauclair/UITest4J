/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Fetches the selected tab index in a {@code JTabbedPane}.
 *
 * @author Christian Rösch
 */
final class JTabbedPaneSelectTabQuery {
	@RunsInEDT
	static int selectedTabIndexOf(final @Nonnull JTabbedPane tabbedPane) {
		return Objects.requireNonNull(execute(tabbedPane::getSelectedIndex));
	}

	private JTabbedPaneSelectTabQuery() {
	}
}