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
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=235">Bug 235</a>.
 *
 * @author Alex Ruiz
 */
class Bug235_findAllNotInSameOrder_Test extends RobotBasedTestCase {
	@Test
	void should_Always_Return_All_Found_Components_In_Same_Order() {
		MyWindow window = MyWindow.createNew();
		TypeMatcher matcher = new TypeMatcher(JTextField.class);
		List<Component> foundFirst = findAll(window, matcher);
		assertThat(foundFirst).hasSize(3);
		window.destroy();
		for (int i = 0; i < 20; i++) {
			window = MyWindow.createNew();
			List<Component> found = findAll(window, matcher);
			assertThat(found.get(0).getName()).isSameAs(foundFirst.get(0).getName());
			assertThat(found.get(1).getName()).isSameAs(foundFirst.get(1).getName());
			assertThat(found.get(2).getName()).isSameAs(foundFirst.get(2).getName());
			window.destroy();
		}
	}

	private List<Component> findAll(MyWindow window, TypeMatcher matcher) {
		ComponentFinder finder = robot.finder();
		return newArrayList(finder.findAll(window, matcher));
	}

	private static class MyWindow extends TestWindow {

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(Bug235_findAllNotInSameOrder_Test.class);
			addComponents(textFieldWithName("one"), textFieldWithName("two"), textFieldWithName("three"));
		}

		@RunsInCurrentThread
		private static JTextField textFieldWithName(String name) {
			JTextField textField = new JTextField(20);
			textField.setName(name);
			return textField;
		}
	}
}
