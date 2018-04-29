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

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Base test case in {@link JTreeDriver} related to showing a pop-up menu at any cell in a {@code JTree}.
 *
 * @author Alex Ruiz
 */
public abstract class JTreeDriver_showPopupMenuAtCell_TestCase extends JTreeDriver_TestCase {
  private JPopupMenu popupMenu;

  @RunsInEDT
  @Override
  void extraSetUp() {
    execute(() -> {
      popupMenu = new JPopupMenu();
      popupMenu.add(new JMenuItem("Hello"));
      tree.addMouseListener(new Listener(popupMenu));
    });
  }

  final JPopupMenu popupMenu() {
    return popupMenu;
  }

  static class Listener extends MouseAdapter {
    private final JPopupMenu popupMenu;

    Listener(JPopupMenu popupMenu) {
      this.popupMenu = popupMenu;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (e.getButton() != MouseEvent.BUTTON3) {
        return;
      }
      Component c = e.getComponent();
      if (!(c instanceof JTree)) {
        return;
      }
      JTree tree = (JTree) c;
      int x = e.getX();
      int y = e.getY();
      int row = tree.getRowForLocation(x, y);
      if (row >= 0) {
        popupMenu.show(tree, x, y);
      }
    }
  }
}
