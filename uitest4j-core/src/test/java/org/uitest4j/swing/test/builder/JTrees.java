/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.builder;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;

import javax.swing.*;
import javax.swing.tree.TreeNode;

import static org.assertj.core.util.Arrays.isNullOrEmpty;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JTree}s.
 *
 * @author Alex Ruiz
 */
public final class JTrees {
	private JTrees() {
	}

	public static JTreeFactory tree() {
		return new JTreeFactory();
	}

	public static class JTreeFactory {
		String name;
		TreeNode root;
		Object[] values;

		public JTreeFactory withRoot(TreeNode newRoot) {
			root = newRoot;
			return this;
		}

		public JTreeFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JTreeFactory withValues(Object... newValues) {
			values = newValues;
			return this;
		}

		@RunsInEDT
		public JTree createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JTree executeInEDT() {
					if (root != null && !isNullOrEmpty(values)) {
						throw new IllegalStateException("Either set root or values, but not both");
					}
					JTree tree = null;
					if (root != null) {
						tree = new JTree(root);
					}
					else if (!isNullOrEmpty(values)) {
						tree = new JTree(values);
					}
					else {
						tree = new JTree();
					}
					tree.setName(name);
					return tree;
				}
			});
		}
	}
}