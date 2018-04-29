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
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JCheckBox}s.
 * 
 * @author Alex Ruiz
 */
public final class JCheckBoxes {
  private JCheckBoxes() {
  }

  public static JCheckBoxFactory checkBox() {
    return new JCheckBoxFactory();
  }

  public static class JCheckBoxFactory {
    String name;
    boolean selected;
    String text;

    public JCheckBoxFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JCheckBoxFactory selected(boolean isSelected) {
      selected = isSelected;
      return this;
    }

    public JCheckBoxFactory withText(String newText) {
      text = newText;
      return this;
    }

    @RunsInEDT
    public JCheckBox createNew() {
      return execute(new GuiQuery<JCheckBox>() {
        @Override
        protected JCheckBox executeInEDT() {
          JCheckBox checkBox = new JCheckBox();
          checkBox.setName(name);
          checkBox.setSelected(selected);
          checkBox.setText(text);
          return checkBox;
        }
      });
    }
  }
}