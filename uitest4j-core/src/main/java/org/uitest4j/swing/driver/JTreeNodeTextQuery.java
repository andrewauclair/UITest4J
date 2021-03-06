/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Objects;

import static org.uitest4j.swing.driver.JTreeMatchingPathQuery.matchingPathWithRootIfInvisible;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the text of a node in a {@code JTree}.
 *
 * @author Alex Ruiz
 */
final class JTreeNodeTextQuery {
	@RunsInEDT
	static @Nullable
	String nodeText(final @Nonnull JTree tree, final int row, final @Nonnull JTreeLocation location,
					final @Nonnull JTreePathFinder pathFinder) {
		return execute(() -> {
			TreePath matchingPath = location.pathFor(tree, row);
			return pathFinder.cellReader().valueAt(tree, Objects.requireNonNull(matchingPath.getLastPathComponent()));
		});
	}

	@RunsInEDT
	static @Nullable
	String nodeText(final @Nonnull JTree tree, final @Nonnull String path,
					final @Nonnull JTreePathFinder pathFinder) {
		return execute(() -> {
			TreePath matchingPath = matchingPathWithRootIfInvisible(tree, path, pathFinder);
			return pathFinder.cellReader().valueAt(tree, Objects.requireNonNull(matchingPath.getLastPathComponent()));
		});
	}

	private JTreeNodeTextQuery() {
	}
}
