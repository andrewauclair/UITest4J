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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestDialog;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.awt.TestContainers.singletonContainerMock;
import static org.uitest4j.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;

/**
 * Tests for {@link ContainerFocusOwnerFinder#focusOwnerOf(Container)}.
 *
 * @author Alex Ruiz
 */
class ContainerFocusOwnerFinder_focusOwnerOf_Test extends SequentialEDTSafeTestCase {
	private MyWindow window;
	private ContainerFocusOwnerFinder finder;

	@Override
	protected void onSetUp() {
		finder = new ContainerFocusOwnerFinder();
		window = MyWindow.createNew();
	}

	@Override
	protected void onTearDown() {
		window.destroy();
	}

	@Test
	void should_Return_Null_If_Container_Is_Not_Window() {
		Container c = singletonContainerMock();
		assertThat(focusOwnerOf(c)).isNull();
	}

	@Test
	void should_Return_Null_If_Window_Is_Not_Showing() {
		assertThat(focusOwnerOf(window)).isNull();
	}

	@Test
	void should_Return_Focus_Owner_In_Window() {
		window.display();
		JTextField focusOwner = window.textBox;
		giveFocusAndWaitTillIsFocused(focusOwner);
		assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
	}

	@Test
	void should_Return_Focus_Owner_In_Owned_Window_When_Top_Window_Does_Not_Have_Focus_Owner() {
		window.display();
		MyDialog dialog = MyDialog.createAndShow(window);
		JButton focusOwner = dialog.button;
		giveFocusAndWaitTillIsFocused(focusOwner);
		assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
	}

	@Test
	void should_Return_Null_If_Top_Window_Or_Owned_Windows_Do_Not_Have_Focus_Owner() {
		window.display();
		MyWindow window2 = MyWindow.createNew();
		window2.display();
		giveFocusAndWaitTillIsFocused(window2.textBox);
		assertThat(focusOwnerOf(window)).isNull();
	}

	@RunsInEDT
	private Component focusOwnerOf(final Container c) {
		return execute(() -> finder.focusOwnerOf(c));
	}

	private static class MyDialog extends TestDialog {
		final JButton button = new JButton("Click me");

		@RunsInEDT
		static MyDialog createAndShow(final Frame owner) {
			MyDialog dialog = execute(() -> new MyDialog(owner));
			dialog.display();
			return dialog;
		}

		private MyDialog(Frame owner) {
			super(owner);
			add(button);
		}
	}

	private static class MyWindow extends TestWindow {
		final JTextField textBox = new JTextField(20);

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(ContainerFocusOwnerFinder.class);
			addComponents(textBox);
		}
	}
}
