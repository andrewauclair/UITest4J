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
import org.uitest4j.swing.hierarchy.ComponentHierarchy;
import org.uitest4j.swing.hierarchy.NewHierarchy;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link FinderDelegate}.
 *
 * @author Alex Ruiz
 */
public abstract class FinderDelegate_TestCase extends SequentialEDTSafeTestCase {
	ComponentHierarchy hierarchy;
	MyWindow window;
	FinderDelegate finder;

	@Override
	protected final void onSetUp() {
		hierarchy = NewHierarchy.ignoreExistingComponents();
		window = MyWindow.createNew(getClass());
		finder = new FinderDelegate();
	}

	@Override
	protected final void onTearDown() {
		window.destroy();
	}

	static class MyWindow extends TestWindow {
		final JTextField textField = new JTextField(10);

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			addComponents(textField);
			textField.setName("textBox");
		}
	}
}
