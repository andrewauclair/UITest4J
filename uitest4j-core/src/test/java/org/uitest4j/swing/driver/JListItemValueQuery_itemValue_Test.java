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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JListCellReader;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JListItemValueQuery#itemValue(JList, int, JListCellReader)}.
 *
 * @author Alex Ruiz
 */
public class JListItemValueQuery_itemValue_Test extends RobotBasedTestCase {
	private JList list;
	private JListCellReader cellReader;

	private static Collection<Object[]> items() {
		return newArrayList(new Object[][]{{0, "Yoda"}, {1, "Luke"}});
	}

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		list = window.list;
		cellReader = new BasicJListCellReader();
	}

	@ParameterizedTest
	@MethodSource("items")
	void should_Return_Item_Value_As_Text(int index, String expectedValue) {
		String actualValue = itemValue(list, index, cellReader);
		assertThat(actualValue).isEqualTo(expectedValue);
	}

	@RunsInEDT
	private static String itemValue(final JList list, final int index, final JListCellReader cellReader) {
		return execute(() -> JListItemValueQuery.itemValue(list, index, cellReader));
	}

	private static class MyWindow extends TestWindow {
		private static final Dimension LIST_SIZE = new Dimension(80, 40);

		final JList list = new JList(array(new Jedi("Yoda"), new Jedi("Luke")));

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JListItemValueQuery.class);
			addComponents(decorate(list));
		}

		private static JScrollPane decorate(JList list) {
			JScrollPane scrollPane = new JScrollPane(list);
			scrollPane.setPreferredSize(LIST_SIZE);
			return scrollPane;
		}
	}
}
