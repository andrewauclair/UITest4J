/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.core.MouseButton.*;
import static org.uitest4j.swing.core.MouseClickInfo.middleButton;

/**
 * Tests for {@link JListItemFixture}.
 *
 * @author Alex Ruiz
 */
class JListItemFixture_withMocks_Test {
	private JListFixture list;
	private int index;

	private JListItemFixture fixture;

	@BeforeEach
	void setUp() {
		list = mock(JListFixture.class);
		index = 8;
		fixture = new JListItemFixture(list, index);
	}

	@Test
	void should_Call_SelectItem_In_JListFixture_And_Return_Self() {
		assertThat(fixture.select()).isSameAs(fixture);
		verify(list).selectItem(index);
	}

	@Test
	void should_Call_UnselectItem_In_JListFixture_And_Return_Self() {
		assertThat(fixture.unselect()).isSameAs(fixture);
		verify(list).unselectItem(index);
	}

	@Test
	void should_Call_ClickItem_In_JListFixture_And_Return_Self() {
		assertThat(fixture.click()).isSameAs(fixture);
		verify(list).clickItem(index);
	}

	@Test
	void should_Call_ClickItem_With_MouseButton_In_JListFixture_And_Return_Self() {
		assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
		verify(list).clickItem(index, MIDDLE_BUTTON, 1);
	}

	@Test
	void should_Call_ClickItem_With_MouseClickInfo_In_JListFixture_And_Return_Self() {
		assertThat(fixture.click(middleButton().times(2))).isSameAs(fixture);
		verify(list).clickItem(index, MIDDLE_BUTTON, 2);
	}

	@Test
	void should_Call_ClickItem_With_Left_Button_Two_Times_In_JListFixture_And_Return_Self() {
		assertThat(fixture.doubleClick()).isSameAs(fixture);
		verify(list).clickItem(index, LEFT_BUTTON, 2);
	}

	@Test
	void should_Call_ClickItem_With_Right_Button_One_Time_In_JListFixture_And_Return_Self() {
		assertThat(fixture.rightClick()).isSameAs(fixture);
		verify(list).clickItem(index, RIGHT_BUTTON, 1);
	}

	@Test
	void should_Call_ShowPopupMenuAt_In_JListFixture_And_Return_Self() {
		JPopupMenuFixture popupMenu = mock(JPopupMenuFixture.class);
		when(list.showPopupMenuAt(index)).thenReturn(popupMenu);
		assertThat(fixture.showPopupMenu()).isSameAs(popupMenu);
		verify(list).showPopupMenuAt(index);
	}

	@Test
	void should_Return_Value_Using_JListFixture() {
		when(list.valueAt(index)).thenReturn("One");
		assertThat(fixture.value()).isEqualTo("One");
		verify(list).valueAt(index);
	}

	@Test
	void should_Call_Drag_In_JListFixture_And_Return_Self() {
		assertThat(fixture.drag()).isSameAs(fixture);
		verify(list).drag(index);
	}

	@Test
	void should_Call_Drop_In_JListFixture_And_Return_Self() {
		assertThat(fixture.drop()).isSameAs(fixture);
		verify(list).drop(index);
	}
}
