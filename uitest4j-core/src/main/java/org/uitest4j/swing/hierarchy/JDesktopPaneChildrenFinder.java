/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.hierarchy;

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.JInternalFrame.JDesktopIcon;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Finds children {@code Component}s in a {@code JDesktopPane}.
 * 
 * @author Yvonne Wang
 */
final class JDesktopPaneChildrenFinder implements ChildrenFinderStrategy {
  @Override
  @RunsInCurrentThread
  @Nonnull public Collection<Component> nonExplicitChildrenOf(@Nonnull Container c) {
    if (!(c instanceof JDesktopPane)) {
		return new ArrayList<>();
    }
    return internalFramesFromIcons(c);
  }

  // From Abbot: add iconified frames, which are otherwise unreachable. For consistency, they are still considered
  // children of the desktop pane.
  @RunsInCurrentThread
  @Nonnull private Collection<Component> internalFramesFromIcons(@Nonnull Container c) {
	  Collection<Component> frames = new ArrayList<>();
    for (Component child : c.getComponents()) {
      if (child instanceof JDesktopIcon) {
        JInternalFrame frame = ((JDesktopIcon) child).getInternalFrame();
        if (frame != null) {
          frames.add(frame);
        }
        continue;
      }
      // OSX puts icons into a dock; handle icon manager situations here
      if (child instanceof Container) {
        frames.addAll(internalFramesFromIcons((Container) child));
      }
    }
    return frames;
  }
}
