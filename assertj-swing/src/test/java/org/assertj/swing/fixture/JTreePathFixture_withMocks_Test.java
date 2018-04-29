/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.fixture;

import org.uitest4j.swing.core.MouseClickInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.uitest4j.swing.core.MouseClickInfo.middleButton;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link JTreePathFixture}.
 *
 * @author Alex Ruiz
 */
class JTreePathFixture_withMocks_Test {
  private JTreeFixture treeFixture;
  private String path;

  private JTreePathFixture fixture;

  @BeforeEach
  void setUp() {
    fixture = new JTreePathFixture(mock(JTreeFixture.class), "root/child");
    treeFixture = fixture.treeFixture();
    path = fixture.path();
  }

  @Test
  void should_Call_ExpandPath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.expand()).isSameAs(fixture);
    verify(treeFixture).expandPath(path);
  }

  @Test
  void should_Call_CollapsePath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.collapse()).isSameAs(fixture);
    verify(treeFixture).collapsePath(path);
  }

  @Test
  void should_Call_SelectPath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.select()).isSameAs(fixture);
    verify(treeFixture).selectPath(path);
  }

  @Test
  void should_Call_UnselectPath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.unselect()).isSameAs(fixture);
    verify(treeFixture).unselectPath(path);
  }

  @Test
  void should_Call_ClickPath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(treeFixture).clickPath(path);
  }

  @Test
  void should_Call_ClickPath_With_MouseButtion_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
    verify(treeFixture).clickPath(path, MIDDLE_BUTTON);
  }

  @Test
  void should_Call_ClickPath_With_MouseClickInfo_In_JTreeFixture_And_Return_Self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.click(info)).isSameAs(fixture);
    verify(treeFixture).clickPath(path, info);
  }

  @Test
  void should_Call_DoubleClickPath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(treeFixture).doubleClickPath(path);
  }

  @Test
  void should_Call_RightClickPath_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(treeFixture).rightClickPath(path);
  }

  @Test
  void should_Call_Drag_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.drag()).isSameAs(fixture);
    verify(treeFixture).drag(path);
  }

  @Test
  void should_Call_Drop_In_JTreeFixture_And_Return_Self() {
    assertThat(fixture.drop()).isSameAs(fixture);
    verify(treeFixture).drop(path);
  }

  @Test
  void should_Return_JPopupMenu_Using_JTreeFixture() {
    JPopupMenuFixture popupMenuFixture = mock(JPopupMenuFixture.class);
    when(treeFixture.showPopupMenuAt(path)).thenReturn(popupMenuFixture);
    assertThat(fixture.showPopupMenu()).isSameAs(popupMenuFixture);
    verify(treeFixture).showPopupMenuAt(path);
  }

  @Test
  void should_Return_Value_Using_JTreeFixture() {
    when(treeFixture.valueAt(path)).thenReturn("Hello");
    assertThat(fixture.value()).isEqualTo("Hello");
    verify(treeFixture).valueAt(path);
  }
}