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

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Uses reflection to toggle the "expand state" of a node in a given {@code JTextComponent}. This task is executed in
 * the event dispatch thread (EDT).
 *
 * @author Yvonne Wang
 */
final class JTreeToggleExpandStateTask {
	@RunsInCurrentThread
	static void toggleExpandState(final @Nonnull JTree tree, final @Nonnull Point pathLocation) {
		TreePath path = tree.getPathForLocation(pathLocation.x, pathLocation.y);
		tree.expandPath(path);
	}

	private JTreeToggleExpandStateTask() {
	}
}