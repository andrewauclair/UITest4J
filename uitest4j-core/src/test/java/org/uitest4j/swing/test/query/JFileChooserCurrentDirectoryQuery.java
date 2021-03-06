/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.query;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.File;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the current directory of a {@code JFileChooser}. This query is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JFileChooserCurrentDirectoryQuery {
	/**
	 * Returns the current directory of the given {@code JFileChooser}. This query is executed in the event dispatch
	 * thread (EDT).
	 *
	 * @param fileChooser the given {@code JFileChooser}.
	 * @return the current directory of the given {@code JFileChooser}.
	 */
	@RunsInEDT
	@Nonnull
	public static File currentDirectoryOf(final @Nonnull JFileChooser fileChooser) {
		File result = execute(fileChooser::getCurrentDirectory);
		return checkNotNull(result);
	}

	private JFileChooserCurrentDirectoryQuery() {
	}
}