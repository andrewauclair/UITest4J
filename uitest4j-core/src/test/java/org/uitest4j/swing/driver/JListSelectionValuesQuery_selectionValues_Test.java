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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JListCellReader;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JListSelectionValuesQuery#selectionValues(JList, JListCellReader)}.
 *
 * @author Alex Ruiz
 */
public class JListSelectionValuesQuery_selectionValues_Test extends RobotBasedTestCase {
	private JList list;
	private JListCellReader cellReader;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		list = window.list;
		cellReader = new BasicJListCellReader();
	}

	@Test
	public void should_Return_Empty_Array_If_JList_Has_No_Selection() {
		List<String> selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
		assertThat(selection).isEmpty();
	}

	@Test
	public void should_Return_Selection_Of_JList_As_Text() {
		setSelectedIndices(list, 0, 1, 2);
		robot.waitForIdle();
		List<String> selection = JListSelectionValuesQuery.selectionValues(list, cellReader);
		assertThat(selection).containsOnly("One", "Two", "Three");
	}

	@RunsInEDT
	private void setSelectedIndices(final JList list, final int... indices) {
		int count = indices.length;
		final int[] toSelect = new int[count];
		System.arraycopy(indices, 0, toSelect, 0, count);
		execute(() -> list.setSelectedIndices(toSelect));
	}

	private static class MyWindow extends TestWindow {
		private static final Dimension LIST_SIZE = new Dimension(80, 40);

		final JList list = new JList(array("One", "Two", "Three", "Four"));

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JListSelectionValuesQuery_selectionValues_Test.class);
			addComponents(decorate(list));
		}

		private static JScrollPane decorate(JList list) {
			JScrollPane scrollPane = new JScrollPane(list);
			scrollPane.setPreferredSize(LIST_SIZE);
			return scrollPane;
		}
	}
}
