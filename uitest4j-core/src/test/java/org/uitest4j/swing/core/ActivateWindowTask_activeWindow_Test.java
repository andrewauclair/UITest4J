/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.timing.Condition;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.test.builder.JFrames.frame;
import static org.uitest4j.swing.test.task.FrameShowTask.packAndShow;
import static org.uitest4j.swing.test.task.WindowDestroyTask.hideAndDispose;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Tests for {@link ActivateWindowTask#activateWindow(java.awt.Window)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ActivateWindowTask_activeWindow_Test extends SequentialEDTSafeTestCase {
	private JFrame frameOne;
	private JFrame frameTwo;

	@Override
	protected void onSetUp() {
		Dimension size = new Dimension(300, 200);
		frameOne = frame().withTitle(concat(testName(), " - One")).createNew();
		packAndShow(frameOne, size);
		frameTwo = frame().withTitle(concat(testName(), " - Two")).createNew();
		packAndShow(frameTwo, size);
	}

	private static String testName() {
		return ActivateWindowTask_activeWindow_Test.class.getSimpleName();
	}

	@Override
	protected void onTearDown() {
		hideAndDispose(frameOne);
		frameTwo.toFront();
		hideAndDispose(frameTwo);
	}

	@Test
	void should_Activate_Window() {
		pause(new HasFocusCondition(frameTwo));
		ActivateWindowTask.activateWindow(frameOne);
		// verify that frameOne was given focus (i.e. was activated)
		pause(new HasFocusCondition(frameOne));
	}

	private static class HasFocusCondition extends Condition {
		private Component c;

		HasFocusCondition(Component c) {
			super("Component has focus");
			this.c = c;
		}

		@Override
		public boolean test() {
			return c.hasFocus();
		}

		@Override
		protected void done() {
			c = null;
		}
	}
}
