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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.lock.ScreenLock;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestDialog;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JFrames.frame;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

/**
 * Tests for {@link WindowChildrenFinder#nonExplicitChildrenOf(Container)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class WindowChildrenFinder_nonExplicitChildrenOf_Test extends EDTSafeTestCase {
	private WindowChildrenFinder finder;

	@BeforeEach
	void setUp() {
		finder = new WindowChildrenFinder();
	}

	@Test
	void should_Return_Empty_Collection_If_Component_Is_Not_Window() {
		Container container = textField().createNew();
		assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
	}

	@Test
	void should_Return_Empty_Collection_If_Component_Is_Null() {
		assertThat(finder.nonExplicitChildrenOf(new Container())).isEmpty();
	}

	@Test
	void should_Return_Empty_Collection_If_Window_Does_Not_Have_Owned_Windows() {
		final JFrame frame = frame().createNew();
		Collection<Component> children = findChildren(finder, frame);
		assertThat(children).isEmpty();
	}

	@Test
	void should_Return_Owned_Windows() {
		ScreenLock.instance().acquire(this);
		TestWindow window = TestWindow.createNewWindow(getClass());
		TestDialog dialog = TestDialog.createNewDialog(window);
		try {
			Collection<Component> children = findChildren(finder, window);
			assertThat(children).containsOnly(dialog);
		}
		finally {
			try {
				dialog.destroy();
				window.destroy();
			}
			finally {
				ScreenLock.instance().release(this);
			}
		}
	}

	@RunsInEDT
	private static Collection<Component> findChildren(final WindowChildrenFinder finder, final Window w) {
		return execute(() -> finder.nonExplicitChildrenOf(w));
	}
}
