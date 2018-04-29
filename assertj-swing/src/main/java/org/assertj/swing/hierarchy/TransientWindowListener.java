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
package org.assertj.swing.hierarchy;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.awt.event.AWTEventListener;

import static javax.swing.SwingUtilities.invokeLater;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.util.AWTEvents.*;

/**
 * Automatic filtering of auto-generated Swing dialogs.
 * 
 * @author Alex Ruiz
 */
public final class TransientWindowListener implements AWTEventListener {
  private final WindowFilter filter;

  TransientWindowListener(@Nonnull WindowFilter filter) {
    this.filter = filter;
  }

  @RunsInEDT
  @Override
  public void eventDispatched(AWTEvent event) {
    AWTEvent e = checkNotNull(event);
    if (wasWindowOpened(e) || wasWindowShown(e)) {
      Window w = sourceOf(e);
      if (w != null) {
        filter(w);
      }
      return;
    }
    if (wasWindowClosed(e)) {
      Window w = sourceOf(e);
      if (w == null) {
        return;
      }
      // *Any* window disposal should result in the window being ignored, at least until it is again displayed.
      if (filter.isIgnored(w)) {
        return;
      }
      filter.implicitlyIgnore(w);
      // Filter this window only *after* any handlers for this event have finished.
      invokeLater(new IgnoreWindowTask(w, filter));
    }
  }

  @Nullable private Window sourceOf(@Nonnull AWTEvent e) {
    return (Window) e.getSource();
  }

  private void filter(@Nonnull Window w) {
    if (filter.isImplicitlyIgnored(w)) {
      filter.recognize(w);
      return;
    }
    // Catch new sub-windows of filtered windows (i.e. dialogs generated by a test harness UI).
    filterIfParentIsFiltered(w);
  }

  private void filterIfParentIsFiltered(@Nonnull Window w) {
    if (!filter.isIgnored(w.getParent())) {
      return;
    }
    filter.ignore(w);
  }
}
