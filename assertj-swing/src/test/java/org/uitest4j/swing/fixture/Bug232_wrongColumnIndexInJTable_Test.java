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
package org.uitest4j.swing.fixture;

import org.uitest4j.swing.data.TableCellByColumnId;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=232" target="_blank">issue 232</a>.
 *
 * @author Alex Ruiz
 */
public class Bug232_wrongColumnIndexInJTable_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    robot.showWindow(window);
  }

  @Test
  public void should_Enter_Value_After_Removing_Column() {
    removeFirstColumn(window.table);
    robot.waitForIdle();
    JTableFixture table = new JTableFixture(robot, window.table);
    table.cell(TableCellByColumnId.row(0).columnId("2")).enterValue("foo");
  }

  private static void removeFirstColumn(final JTable table) {
    execute(() -> {
      TableColumnModel columnModel = table.getColumnModel();
      columnModel.removeColumn(columnModel.getColumn(0));
    });
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JTable table = new JTable(2, 2);

    private MyWindow() {
      super(Bug232_wrongColumnIndexInJTable_Test.class);
      addComponents(table);
      TableColumnModel columnModel = table.getColumnModel();
      columnModel.getColumn(0).setIdentifier("1");
      columnModel.getColumn(1).setIdentifier("2");
    }
  }
}
