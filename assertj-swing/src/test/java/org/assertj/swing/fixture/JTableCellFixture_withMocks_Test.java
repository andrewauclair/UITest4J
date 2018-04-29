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
import org.uitest4j.swing.data.TableCell;
import org.assertj.swing.driver.JTableDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.core.MouseButton.*;
import static org.uitest4j.swing.core.MouseClickInfo.middleButton;
import static org.uitest4j.swing.data.TableCell.row;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link JTableCellFixture}.
 *
 * @author Alex Ruiz
 */
class JTableCellFixture_withMocks_Test {
  private JTableFixture table;
  private TableCell cell;
  private JTableDriver driver;
  private JTable target;

  private JTableCellFixture fixture;

  @BeforeEach
  void setUp() {
    table = mock(JTableFixture.class);
    cell = row(6).column(8);
    driver = mock(JTableDriver.class);
    target = mock(JTable.class);
    fixture = new JTableCellFixture(table, cell, target, driver);
  }

  @Test
  void should_Call_Select_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.select()).isSameAs(fixture);
    verify(table).selectCell(cell);
  }

  @Test
  void should_Call_Unselect_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.unselect()).isSameAs(fixture);
    verify(table).unselectCell(cell);
  }

  @Test
  void should_Call_Click_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(table).click(cell, LEFT_BUTTON);
  }

  @Test
  void should_Call_Click_With_MouseClickInfo_In_JTableFixture_And_Return_Self() {
    MouseClickInfo info = middleButton().times(3);
    assertThat(fixture.click(info)).isSameAs(fixture);
    verify(table).click(cell, info);
  }

  @Test
  void should_Call_Click_With_MouseButton_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.click(MIDDLE_BUTTON)).isSameAs(fixture);
    verify(table).click(cell, MIDDLE_BUTTON);
  }

  @Test
  void should_Call_Click_With_Left_Button_Two_Times_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(table).click(cell, LEFT_BUTTON, 2);
  }

  @Test
  void should_Call_Click_With_Right_Button_One_Time_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(table).click(cell, RIGHT_BUTTON);
  }

  @Test
  void should_Call_StartCellEditing_In_Driver_And_Return_Self() {
    assertThat(fixture.startEditing()).isSameAs(fixture);
    verify(driver).startCellEditing(target, cell);
  }

  @Test
  void should_Call_StopCellEditing_In_Driver_And_Return_Self() {
    assertThat(fixture.stopEditing()).isSameAs(fixture);
    verify(driver).stopCellEditing(target, cell);
  }

  @Test
  void should_Call_CancelCellEditing_In_Driver_And_Return_Self() {
    assertThat(fixture.cancelEditing()).isSameAs(fixture);
    verify(driver).cancelCellEditing(target, cell);
  }

  @Test
  void should_Return_Cell_Editor_Using_Driver() {
    Component editor = mock(Component.class);
    when(driver.cellEditor(target, cell)).thenReturn(editor);
    assertThat(fixture.editor()).isSameAs(editor);
    verify(driver).cellEditor(target, cell);
  }

  @Test
  void should_Call_EnterValue_In_Driver_And_Return_Self() {
    assertThat(fixture.enterValue("Hello")).isSameAs(fixture);
    verify(driver).enterValueInCell(target, cell, "Hello");
  }

  @Test
  void should_Call_RequireValue_With_Text_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.requireValue("Six")).isSameAs(fixture);
    verify(table).requireCellValue(cell, "Six");
  }

  @Test
  void should_Call_RequireValue_With_Pattern_In_JTableFixture_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.requireValue(pattern)).isSameAs(fixture);
    verify(table).requireCellValue(cell, pattern);
  }

  @Test
  void should_Return_Font_Using_JTableFixture() {
    FontFixture font = mock(FontFixture.class);
    when(table.fontAt(cell)).thenReturn(font);
    assertThat(fixture.font()).isSameAs(font);
    verify(table).fontAt(cell);
  }

  @Test
  void should_Return_Background_Using_JTableFixture() {
    ColorFixture color = mock(ColorFixture.class);
    when(table.backgroundAt(cell)).thenReturn(color);
    assertThat(fixture.background()).isSameAs(color);
    verify(table).backgroundAt(cell);
  }

  @Test
  void should_Return_Foreground_Using_JTableFixture() {
    ColorFixture color = mock(ColorFixture.class);
    when(table.foregroundAt(cell)).thenReturn(color);
    assertThat(fixture.foreground()).isSameAs(color);
    verify(table).foregroundAt(cell);
  }

  @Test
  void should_Return_Value_Using_JTableFixture() {
    when(table.valueAt(cell)).thenReturn("Hello");
    assertThat(fixture.value()).isEqualTo("Hello");
    verify(table).valueAt(cell);
  }

  @Test
  void should_Call_Drag_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.drag()).isSameAs(fixture);
    verify(table).drag(cell);
  }

  @Test
  void should_Call_Drop_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.drop()).isSameAs(fixture);
    verify(table).drop(cell);
  }

  @Test
  void should_Call_RequireEditable_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(table).requireEditable(cell);
  }

  @Test
  void should_Call_RequireNotEditable_In_JTableFixture_And_Return_Self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(table).requireNotEditable(cell);
  }

  @Test
  void should_Return_JPopupMenu_Using_JTableFixture() {
    JPopupMenuFixture popupMenu = mock(JPopupMenuFixture.class);
    when(table.showPopupMenuAt(cell)).thenReturn(popupMenu);
    assertThat(fixture.showPopupMenu()).isSameAs(popupMenu);
    verify(table).showPopupMenuAt(cell);
  }

  @Test
  void should_Return_Row() {
    assertThat(fixture.row()).isEqualTo(cell.row);
  }

  @Test
  void should_Return_Column() {
    assertThat(fixture.column()).isEqualTo(cell.column);
  }
}
