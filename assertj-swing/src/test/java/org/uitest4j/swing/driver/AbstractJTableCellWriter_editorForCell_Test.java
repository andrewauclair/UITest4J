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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link AbstractJTableCellWriter#editorForCell(javax.swing.JTable, int, int)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class AbstractJTableCellWriter_editorForCell_Test extends RobotBasedTestCase {
  private TableDialogEditDemoWindow frame;
  private AbstractJTableCellWriter writer;

  private static Collection<Object[]> cellEditors() {
    return newArrayList(new Object[][] { { 0, 2, JComboBox.class }, { 0, 3, JTextField.class },
        { 0, 4, JCheckBox.class } });
  }

  @Override
  protected void onSetUp() {
    writer = new AbstractJTableCellWriterStub(robot);
    frame = TableDialogEditDemoWindow.createNew(AbstractJTableCellWriter_editorForCell_Test.class);
    robot.showWindow(frame, new Dimension(500, 100));
  }

  @ParameterizedTest
  @MethodSource("cellEditors")
  void shouldReturnEditorForCell(int row, int column, Class<Component> editorType) {
    Component editor = writer.editorForCell(frame.table, row, column);
    assertThat(editor).isNotNull().isInstanceOf(editorType);
  }
}
