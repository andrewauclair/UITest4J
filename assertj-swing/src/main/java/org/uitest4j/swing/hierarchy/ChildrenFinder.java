/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.hierarchy;

import org.assertj.core.util.VisibleForTesting;
import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Find children {@code Component}s in an AWT or Swing {@code Container}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class ChildrenFinder {
	private static List<ChildrenFinderStrategy> strategies = Arrays.asList(new JDesktopPaneChildrenFinder(),
			new JMenuChildrenFinder(), new WindowChildrenFinder());

	@RunsInCurrentThread
	@Nonnull
	Collection<Component> childrenOf(@Nonnull Component c) {
		if (!(c instanceof Container)) {
			return new ArrayList<>();
		}
		Container container = (Container) c;
		List<Component> children = new ArrayList<>(Arrays.asList(container.getComponents()));
		children.addAll(nonExplicitChildrenOf(container));
		return children;
	}

	@Nonnull
	private Collection<Component> nonExplicitChildrenOf(@Nonnull Container c) {
		Collection<Component> children = new ArrayList<>();
		for (ChildrenFinderStrategy s : strategies) {
			children.addAll(s.nonExplicitChildrenOf(c));
		}
		return children;
	}

	@VisibleForTesting
	static @Nonnull
	List<ChildrenFinderStrategy> strategies() {
		return new ArrayList<>(strategies);
	}

	@VisibleForTesting
	static void replaceStrategiesWith(@Nonnull List<ChildrenFinderStrategy> newStrategies) {
		strategies = new ArrayList<>(newStrategies);
	}
}
