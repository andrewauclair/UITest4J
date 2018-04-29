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
package org.assertj.swing.core;

import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Base test case for {@link BasicComponentFinder}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class BasicComponentFinder_TestCase extends SequentialEDTSafeTestCase {
  BasicComponentFinder finder;
  MyWindow window;

  @Override
  protected final void onSetUp() {
    finder = (BasicComponentFinder) BasicComponentFinder.finderWithNewAwtHierarchy();
    window = MyWindow.createNew(getClass());
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
    beforeReleasingScreenLock();
  }

  void beforeReleasingScreenLock() {
  }

  static class MyWindow extends TestWindow {
    final JButton button = new JButton("A Button");
    final JLabel label = new JLabel("A Label");
    final JTextField textField1 = new JTextField("TextField 1");
    final JTextField textField2 = new JTextField("TextField 2");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(label, button, textField1, textField2);
      label.setLabelFor(button);
      button.setName("button");
    }
  }
}
