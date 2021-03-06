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

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Arrays.sort;
import static org.uitest4j.swing.driver.JTreeMatchingPathQuery.matchingPathWithRootIfInvisible;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Verifies that a {@code JTree} has the expected selection.
 *
 * @author Alex Ruiz
 */
final class JTreeVerifySelectionTask {
	@RunsInEDT
	static void checkHasSelection(final @Nonnull JTree tree, final @Nonnull int[] selection,
								  final @Nonnull Supplier<String> errMsg) {
		execute(() -> checkSelection(tree, selection, errMsg));
	}

	@RunsInCurrentThread
	private static void checkSelection(@Nonnull JTree tree, @Nonnull int[] selection, @Nonnull Supplier<String> errMsg) {
		int[] selectionRows = tree.getSelectionRows();
		if (selectionRows == null || selectionRows.length == 0) {
			failNoSelection(errMsg);
			return;
		}
		sort(selection);
		if (Arrays.equals(selectionRows, selection)) {
			return;
		}
		failNotEqualSelection(errMsg, selection, selectionRows);
	}

	private static void failNotEqualSelection(@Nonnull Supplier<String> errMsg, @Nonnull int[] expected, @Nonnull int[] actual) {
		String format = "[%s] expecting selection:<%s> but was:<%s>";
		String msg = String.format(format, errMsg.get(), Arrays.toString(expected), Arrays.toString(actual));
		throw new AssertionFailedError(msg);
	}

	@RunsInEDT
	static void checkHasSelection(final @Nonnull JTree tree, final @Nonnull String[] selection,
								  final @Nonnull JTreePathFinder pathFinder, final @Nonnull Supplier<String> errMsg) {
		execute(() -> checkSelection(tree, selection, pathFinder, errMsg));
	}

	@RunsInCurrentThread
	private static void checkSelection(@Nonnull JTree tree, @Nonnull String[] selection,
									   @Nonnull JTreePathFinder pathFinder, @Nonnull Supplier<String> errMsg) {
		TreePath[] selectionPaths = tree.getSelectionPaths();
		if (selectionPaths == null || selectionPaths.length == 0) {
			failNoSelection(errMsg);
			return;
		}
		int selectionCount = selection.length;
		if (selectionCount != selectionPaths.length) {
			failNotEqualSelection(errMsg, selection, selectionPaths);
		}
		for (int i = 0; i < selectionCount; i++) {
			TreePath expected = matchingPathWithRootIfInvisible(tree, Objects.requireNonNull(selection[i]), pathFinder);
			TreePath actual = selectionPaths[i];
			if (!Objects.equals(expected, actual)) {
				failNotEqualSelection(errMsg, selection, selectionPaths);
			}
		}
	}

	private static void failNotEqualSelection(@Nonnull Supplier<String> errMsg, @Nonnull String[] expected,
											  @Nonnull TreePath[] actual) {
		String format = "[%s] expecting selection:<%s> but was:<%s>";
		String msg = String.format(format, errMsg.get(), Arrays.toString(expected), Arrays.toString(actual));
		throw new AssertionFailedError(msg);
	}

	private static void failNoSelection(final @Nonnull Supplier<String> errMessage) {
		throw new AssertionFailedError(String.format("[%s] No selection", errMessage.get()));
	}

	@RunsInEDT
	static void checkNoSelection(final @Nonnull JTree tree, final @Nonnull Supplier<String> errMsg) {
		execute(() -> {
			if (tree.getSelectionCount() == 0) {
				return;
			}
			String format = "[%s] expected no selection but was:<%s>";
			String message = String.format(format, errMsg.get(), Arrays.toString(tree.getSelectionPaths()));
			throw new AssertionFailedError(message);
		});
	}

	private JTreeVerifySelectionTask() {
	}
}
