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
package org.uitest4j.swing.hierarchy;

import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.builder.JTextFields.textField;

/**
 * Tests for {@link JMenuChildrenFinder#nonExplicitChildrenOf(Container)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JMenuChildrenFinder_nonExplicitChildrenOf_Test extends EDTSafeTestCase {
  private JMenuChildrenFinder finder;

  @BeforeEach
  void setUp() {
    finder = new JMenuChildrenFinder();
  }

  @Test
  void should_Return_Empty_Collection_If_Component_Is_Not_JMenu() {
    Container container = textField().createNew();
    assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
  }

  @Test
  void should_Return_Empty_Collection_If_Component_Is_Null() {
    assertThat(finder.nonExplicitChildrenOf(new Container())).isEmpty();
  }

  @Test
  void should_Return_JPopupMenu_If_Component_Is_JMenu() {
    ScreenLock.instance().acquire(this);
    final MyWindow window = MyWindow.createNew();
    Collection<Component> children = execute(() -> finder.nonExplicitChildrenOf(window.menu));
    try {
      assertThat(children).containsOnly(popupMenuOf(window.menu));
    } finally {
      try {
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }

  @RunsInEDT
  private static JPopupMenu popupMenuOf(final JMenu menu) {
    return execute(() -> menu.getPopupMenu());
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JMenu menu = new JMenu("Menu");

    private MyWindow() {
      super(JMenuChildrenFinder_nonExplicitChildrenOf_Test.class);
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      setJMenuBar(menuBar);
    }
  }
}
