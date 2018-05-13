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
import org.uitest4j.swing.cell.JComboBoxCellReader;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.uitest4j.swing.util.TextMatcher;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JComboBoxMatchingItemQuery#matchingItemIndex(JComboBox, TextMatcher, JComboBoxCellReader)}.
 *
 * @author Alex Ruiz
 */
public class JComboBoxMatchingItemQuery_matchingItemIndex_Test extends RobotBasedTestCase {
	private JComboBox comboBox;
	private JComboBoxCellReader cellReader;
	private TextMatcher matcher;

	@Override
	protected void onSetUp() {
		cellReader = new BasicJComboBoxCellReader();
		matcher = mock(TextMatcher.class);
		MyWindow window = MyWindow.createNew();
		comboBox = window.comboBox;
	}

	@Test
	public void should_Return_Matching_Indices() {
		when(matcher.isMatching("aaa")).thenReturn(false);
		when(matcher.isMatching("bbb")).thenReturn(true);
		int matchingIndex = JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, matcher, cellReader);
		assertThat(matchingIndex).isEqualTo(1);
	}

	@Test
	public void should_Return_Negative_One_If_No_Matching_Indices_Found() {
		when(matcher.isMatching("aaa")).thenReturn(false);
		when(matcher.isMatching("bbb")).thenReturn(false);
		when(matcher.isMatching("ccc")).thenReturn(false);
		int matchingIndex = JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, matcher, cellReader);
		assertThat(matchingIndex).isEqualTo(-1);
	}

	private static class MyWindow extends TestWindow {
		final JComboBox comboBox = new JComboBox(array("aaa", "bbb", "ccc"));

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JComboBoxMatchingItemQuery.class);
			addComponents(comboBox);
		}
	}
}
