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

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Makes a {@code JTextComponent} editable. This task is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JTextComponentSetEditableTask {
	@RunsInEDT
	static void setTextFieldEditable(final @Nonnull JTextField textField, final boolean editable) {
		execute(() -> textField.setEditable(editable));
	}

	private JTextComponentSetEditableTask() {
	}
}
