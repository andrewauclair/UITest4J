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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link NameMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public abstract class NameMatcher_TestCase extends SequentialEDTSafeTestCase {
	static final String LABEL_TEXT = "my button";

	MyWindow window;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew(getClass());
	}

	@Override
	protected final void onTearDown() {
		window.destroy();
	}

	static class MyWindow extends TestWindow {
		final JButton button = new JButton("A Button");

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			addComponents(button);
			button.setName(LABEL_TEXT);
		}
	}
}
