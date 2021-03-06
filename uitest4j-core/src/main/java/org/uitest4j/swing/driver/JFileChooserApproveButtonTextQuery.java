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
import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the text used in the "approve button" of a {@code JFileChooser}. This action is executed in the event
 * dispatch thread (EDT).
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @see JFileChooser#getApproveButtonText()
 * @see javax.swing.plaf.FileChooserUI#getApproveButtonText(JFileChooser)
 */
final class JFileChooserApproveButtonTextQuery {
	@RunsInEDT
	static @Nullable
	String approveButtonTextFrom(final @Nonnull JFileChooser fileChooser) {
		return execute(() -> {
			String text = fileChooser.getApproveButtonText();
			if (!Strings.isNullOrEmpty(text)) {
				return text;
			}
			return fileChooser.getUI().getApproveButtonText(fileChooser);
		});
	}

	private JFileChooserApproveButtonTextQuery() {
	}
}