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
package org.uitest4j.swing.driver;

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TableRenderDemo;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTableCellEditorQuery#cellEditorIn(JTable, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableCellEditorQuery_cellEditorIn_Test extends RobotBasedTestCase {
  private JTable table;

  private static Collection<Object[]> editorTypes() {
    return newArrayList(new Object[][] { { 2, JComboBox.class }, { 3, JTextComponent.class }, { 4, JCheckBox.class } });
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    table = window.table;
  }

  @ParameterizedTest
  @MethodSource("editorTypes")
  void should_Return_Editor_Component_From_JTable_Cell(int column, Class<?> editorType) {
    int row = 0;
    Component editor = cellEditorIn(table, row, column);
    assertThat(editor).isInstanceOf(editorType);
  }

  @RunsInEDT
  private static Component cellEditorIn(final JTable table, final int row, final int column) {
    return execute(() -> JTableCellEditorQuery.cellEditorIn(table, row, column));
  }

  private static class MyWindow extends TestWindow {
    final JTable table;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(JTableCellEditorQuery_cellEditorIn_Test.class);
      TableRenderDemo newContentPane = new TableRenderDemo();
      newContentPane.setOpaque(true); // content panes must be opaque
      setContentPane(newContentPane);
      table = newContentPane.table;
    }
  }
}
