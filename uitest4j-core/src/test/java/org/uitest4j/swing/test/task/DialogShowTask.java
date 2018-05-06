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
package org.uitest4j.swing.test.task;

import org.uitest4j.swing.timing.Condition;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.uitest4j.swing.query.ComponentShowingQuery.isShowing;
import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Makes a {@code Dialog} visible.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class DialogShowTask {
  @RunsInCurrentThread
  public static void packAndShow(@Nonnull Dialog dialog, @Nonnull Dimension preferredSize) {
    dialog.setPreferredSize(preferredSize);
    packAndShow(dialog);
  }

  @RunsInCurrentThread
  public static void packAndShow(final @Nonnull Dialog dialog) {
    dialog.pack();
    dialog.setVisible(true);
  }

  @RunsInEDT
  public static void waitForShowing(final @Nonnull Dialog dialog) {
    pause(new Condition("Dialog is showing") {
      @Override
      public boolean test() {
        return isShowing(dialog);
      }
    });
  }

  private DialogShowTask() {
  }
}
