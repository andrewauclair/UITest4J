/*
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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns how many children a node in a {@code JTree} has. This query is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JTreeChildOfPathCountQuery {
	@RunsInEDT
	static int childCount(final @Nonnull JTree tree, final @Nonnull TreePath path) {
		return Objects.requireNonNull(execute(() -> tree.getModel().getChildCount(path.getLastPathComponent())));
	}

	private JTreeChildOfPathCountQuery() {
	}
}