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
import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.recorder.ClickRecorder;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import static org.assertj.core.util.Arrays.array;

/**
 * Tests for {@link JTreeDriver#selectPath(javax.swing.JTree, String)}.
 *
 * @author Alex Ruiz
 */
public class JTreeDriver_selectPath_Test extends JTreeDriver_selectCell_TestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	@Test
	void should_Throw_Error_If_Path_Not_Found() {
		showWindow();
		ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> driver.selectPath(tree, "another"), "Unable to find path 'another'");
	}

	@Test
	void should_Throw_Error_If_JTree_Is_Disabled() {
		disableTree();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectPath(tree, "root/branch1"));
	}

	@Test
	void should_Not_Do_Anything_If_Cell_Is_Already_Selected() {
		showWindow();
		clearTreeSelection();
		select(pathToBranch_1_1_1());
		ClickRecorder recorder = clickRecorder.attachDirectlyTo(tree);
		driver.selectPath(tree, "root/branch1/branch1.1/branch1.1.1");
		recorder.wasNotClicked();
		requireSelectedPaths("root/branch1/branch1.1/branch1.1.1");
	}

	@RunsInEDT
	private TreePath pathToBranch_1_1_1() {
		DefaultMutableTreeNode root = rootOf(tree);
		DefaultMutableTreeNode branch_1 = firstChildOf(root);
		DefaultMutableTreeNode branch_1_1 = firstChildOf(branch_1);
		return new TreePath(array(root, branch_1, branch_1_1, firstChildOf(branch_1_1)));
	}

	@Test
	void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectPath(tree, "root/branch1"));
	}
}
