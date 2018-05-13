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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.timing.Condition;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.query.JInternalFrameIconifiedQuery.isIconified;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Iconifies a given {@code JInternalFrame}. This task is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunsInEDT
final class JInternalFrameIconifyTask {
	static void iconify(final @Nonnull JInternalFrame internalFrame) {
		execute(() -> internalFrame.setIcon(true));
		pause(new Condition("JInternalFrame is iconified") {
			@Override
			public boolean test() {
				return isIconified(internalFrame);
			}
		});
	}

	private JInternalFrameIconifyTask() {
	}
}