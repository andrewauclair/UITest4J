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
package org.assertj.swing.test.builder;

import org.assertj.swing.edt.GuiQuery;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;

import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JFrame}s.
 *
 * @author Alex Ruiz
 */
public final class JFrames {
  private JFrames() {
  }

  public static JFrameFactory frame() {
    return new JFrameFactory();
  }

  public static class JFrameFactory {
    private String name;
    private boolean resizable = true;
    private String title;

    public JFrameFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JFrameFactory withTitle(String newTitle) {
      title = newTitle;
      return this;
    }

    public JFrameFactory resizable(boolean shouldBeResizable) {
      resizable = shouldBeResizable;
      return this;
    }

    @RunsInEDT
    public JFrame createAndShow() {
      return execute(new GuiQuery<JFrame>() {
        @Override
        protected JFrame executeInEDT() {
          JFrame frame = create();
          frame.pack();
          frame.setVisible(true);
          return frame;
        }
      });
    }

    @RunsInEDT
    public JFrame createNew() {
      return execute(this::create);
    }

    @RunsInCurrentThread
    private JFrame create() {
      JFrame frame = new JFrame();
      frame.setName(name);
      frame.setTitle(title);
      frame.setResizable(resizable);
      frame.setPreferredSize(new Dimension(200, 200));
      return frame;
    }
  }
}