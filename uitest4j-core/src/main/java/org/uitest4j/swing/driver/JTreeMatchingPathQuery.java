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

import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.TreePath;

import java.util.Objects;

import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JTreeAddRootIfInvisibleTask.addRootIfInvisible;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Finds a path in a {@code JTree} that matches a given {@code String}. This query is executed in the event dispatch
 * thread (EDT).
 *
 * @author Alex Ruiz
 *
 * @see JTreePathFinder
 */
final class JTreeMatchingPathQuery {
  @RunsInEDT
  static @Nonnull TreePath verifyJTreeIsReadyAndFindMatchingPath(final @Nonnull JTree tree, final @Nonnull String path,
                                                                 final @Nonnull JTreePathFinder pathFinder) {
    TreePath result = execute(new GuiQuery<>() {
		@Override
		@Nonnull
		protected TreePath executeInEDT() {
			checkEnabledAndShowing(tree);
			return matchingPathWithRootIfInvisible(tree, path, pathFinder);
		}
	});
    return Objects.requireNonNull(result);
  }

  @RunsInEDT
  static @Nonnull TreePath matchingPathFor(final @Nonnull JTree tree, final @Nonnull String path,
                                           final @Nonnull JTreePathFinder pathFinder) {
    TreePath result = execute(() -> matchingPathWithRootIfInvisible(tree, path, pathFinder));
    return Objects.requireNonNull(result);
  }

  @RunsInCurrentThread
  static @Nonnull TreePath matchingPathWithRootIfInvisible(@Nonnull JTree tree, @Nonnull String path,
                                                           @Nonnull JTreePathFinder pathFinder) {
    TreePath matchingPath = pathFinder.findMatchingPath(tree, path);
    return addRootIfInvisible(tree, matchingPath);
  }

  private JTreeMatchingPathQuery() {
  }
}