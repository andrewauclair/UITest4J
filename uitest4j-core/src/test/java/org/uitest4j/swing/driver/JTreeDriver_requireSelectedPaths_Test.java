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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import static org.assertj.core.util.Arrays.array;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTreeDriver#requireSelection(javax.swing.JTree, String[])}.
 *
 * @author Alex Ruiz
 */
class JTreeDriver_requireSelectedPaths_Test extends JTreeDriver_selectCell_TestCase {
	@Test
	void should_Pass_If_Single_Cell_Is_Selected() {
		selectFirstChildOfRoot();
		driver.requireSelection(tree, array("root/branch1"));
	}

	@Test
	void should_Pass_If_Cells_Are_Selected() {
		selectBranch1AndBranch1_1();
		driver.requireSelection(tree, array("root/branch1", "root/branch1/branch1.1"));
	}

	@RunsInEDT
	private void selectBranch1AndBranch1_1() {
		DefaultMutableTreeNode root = rootOf(tree);
		TreeNode branch1 = firstChildOf(root);
		TreePath root_branch1 = new TreePath(array(root, branch1));
		TreePath root_branch1_Branch1_1 = new TreePath(array(root, branch1, firstChildOf(branch1)));
		select(root_branch1, root_branch1_Branch1_1);
	}

	@Test
	void should_Throw_Error_If_Expected_Array_Of_Paths_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.requireSelection(tree, (String[]) null));
	}

	@Test
	void should_Fail_If_JTree_Does_Not_Have_Selection() {
		clearTreeSelection();
		ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(tree, array("root/branch1")), "property:'selection'", "No selection");
	}

	@Test
	void should_Fail_If_Selection_Is_Not_Equal_To_Expected() {
		selectFirstChildOfRoot();
		ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSelection(tree, array("root/branch2")), "property:'selection'", "expecting selection:<[root/branch2]> but was:<[[root, branch1]]>");
	}
}
