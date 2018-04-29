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

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.util.Arrays.isNullOrEmpty;
import static org.assertj.core.util.Lists.emptyList;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Find children {@code Component}s in a {@code Window}.
 * 
 * @author Yvonne Wang
 */
final class WindowChildrenFinder implements ChildrenFinderStrategy {
  @RunsInCurrentThread
  @Override
  @Nonnull public Collection<Component> nonExplicitChildrenOf(@Nonnull Container c) {
    if (!(c instanceof Window)) {
      return emptyList();
    }
    return ownedWindows((Window) c);
  }

  @RunsInCurrentThread
  @Nonnull private Collection<Component> ownedWindows(Window w) {
    return windows(w.getOwnedWindows());
  }

  @Nonnull private Collection<Component> windows(@Nonnull Component[] windows) {
    if (isNullOrEmpty(windows)) {
      return emptyList();
    }
    return newArrayList(windows);
  }
}
