/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Adds the root node of a {@code JTree} to a given path if such root is invisible.
 * </p>
 *
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT). Client code must call methods in this class from the EDT.
 * </p>
 *
 * @author Alex Ruiz
 */
final class JTreeAddRootIfInvisibleTask {
	/*
	 * Adds the root node to the path, only if the JTree has an invisible root. If this is not done, a path missing the
	 * root node cannot be expanded (issue 293).
	 */
	@RunsInCurrentThread
	static @Nonnull
	TreePath addRootIfInvisible(@Nonnull JTree tree, @Nonnull TreePath path) {
		Object root = tree.getModel().getRoot();
		if (tree.isRootVisible() || root == null) {
			return path;
		}
		// root is invisible but path already contains root
		if (path.getPathCount() > 0 && root == path.getPathComponent(0)) {
			return path;
		}
		Object[] pathAsArray = path.getPath();
		List<Object> newPath = new ArrayList<>(Arrays.asList(pathAsArray));
		newPath.add(0, root);
		return new TreePath(newPath.toArray());
	}

	private JTreeAddRootIfInvisibleTask() {
	}
}
