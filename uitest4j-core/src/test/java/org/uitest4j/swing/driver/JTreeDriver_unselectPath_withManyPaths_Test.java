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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTreeDriver#unselectPath(javax.swing.JTree, String)}.
 *
 * @author Christian Rösch
 */
class JTreeDriver_unselectPath_withManyPaths_Test extends JTreeDriver_selectCell_TestCase {
	private static Collection<Object[]> paths() {
		return newArrayList(new Object[][]{{"root/branch1"}, {"root/branch1/branch1.2"}, {"root"}});
	}

	@ParameterizedTest
	@MethodSource("paths")
	void should_Unelect_Cell(String treePath) {
		showWindow();
		clearTreeSelection();
		driver.selectPath(tree, treePath);
		requireThatPathIsSelected(treePath);
		driver.unselectPath(tree, treePath);
		requireNoSelection();
	}

	@RunsInEDT
	private void requireThatPathIsSelected(String treePath) {
		assertThat(textOf(selectionPathOf(tree))).isEqualTo(treePath);
	}

	@RunsInEDT
	private static TreePath selectionPathOf(final JTree tree) {
		return execute(tree::getSelectionPath);
	}
}
