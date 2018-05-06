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

import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JFrames.frame;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=159" target="_blank">Bug 159</a>.
 * 
 * @author Alex Ruiz
 */
class Bug159_moveParentToFrontWhenClickingMenuItem_Test extends RobotBasedTestCase {
  private ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private static final int DELAY_BEFORE_SHOWING_MENU = 2000;

  private static Logger logger = Logger.getAnonymousLogger();

    private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    window.display();
      JFrame frameToFocus = frame().withTitle("To Focus").createNew();
    robot.showWindow(frameToFocus, new Dimension(300, 200));
    robot.focus(frameToFocus);
  }

  @Test
  void should_Select_Menu_Item_From_Menu_Bar() {
    JMenuItem menuItem = window.menuItemFromMenuBar;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pauseBeforeShowingMenu();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(menuItem);
    fixture.click();
    recorder.wasClicked();
  }

  @Test
  void should_Select_Menu_Item_From_Popup_Menu() {
    JMenuItem menuItem = window.menuItemFromPopupMenu;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pauseBeforeShowingMenu();
    robot.showPopupMenu(window.textField);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(menuItem);
    fixture.click();
    recorder.wasClicked();
  }

  private void pauseBeforeShowingMenu() {
    int delay = DELAY_BEFORE_SHOWING_MENU;
    logger.info(concat("Pausing for ", delay, " ms before showing menu"));
    pause(delay);
  }

  private JMenuItemFixture fixtureFor(JMenuItem menuItem) {
    return new JMenuItemFixture(robot, menuItem);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JMenuItem menuItemFromMenuBar = new JMenuItem("New");
    final JMenuItem menuItemFromPopupMenu = new JMenuItem("Cut");
    final JTextField textField;

    private MyWindow() {
      super(Bug159_moveParentToFrontWhenClickingMenuItem_Test.class);
      setJMenuBar(new JMenuBar());
      JMenu menuFile = new JMenu("File");
      menuFile.add(menuItemFromMenuBar);
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(200, 100));
      textField = new JTextField(20);
      textField.setComponentPopupMenu(popupMenu());
      add(textField);
    }

    private JPopupMenu popupMenu() {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenu menuEdit = new JMenu("Edit");
      menuEdit.add(menuItemFromPopupMenu);
      popupMenu.add(menuEdit);
      popupMenu.setName("popupMenu");
      return popupMenu;
    }
  }
}
