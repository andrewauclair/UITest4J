/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.task;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Selects/deselects a Swing {@code AbstractButton}. This task is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
public final class AbstractButtonSetSelectedTask {
	@RunsInEDT
	public static void setSelected(final @Nonnull AbstractButton button, final boolean selected) {
		execute(() -> button.setSelected(selected));
	}

	private AbstractButtonSetSelectedTask() {
	}
}