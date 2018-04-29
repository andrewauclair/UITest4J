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

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestDialog;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.finder.WindowFinder.findDialog;

/**
 * Tests lookup of a modal dialog. This test tries to reproduce the problem reported at <a
 * href="http://groups.google.com/group/easytesting/browse_thread/thread/c42bd103c28d6a1a" target="_blank">this mailing
 * list message</a>.
 *
 * @author Alex Ruiz
 */
public class ModalDialogLookupTest extends RobotBasedTestCase {
  private MyWindow frame;

  @Override
  protected void onSetUp() {
    frame = MyWindow.createNew();
    robot.showWindow(frame);
  }

  @Test
  void should_Show_Modal_Dialog_And_Not_Block() {
    FrameFixture frameFixture = new FrameFixture(robot, frame);
    frameFixture.button("launch").click();
    DialogFixture dialogFixture = findDialog(TestDialog.class).using(robot);
    assertThat(dialogFixture.target()).isSameAs(frame.dialog);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JButton button = new JButton("Launch");
    final TestDialog dialog = TestDialog.createNewDialog(this);

    private MyWindow() {
      super(ModalDialogLookupTest.class);
      button.setName("launch");
      add(button);
      dialog.setModal(true);
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          dialog.setVisible(true);
        }
      });
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
