/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.swing.TreeNodeFactory.node;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=263">Bug 263</a>.
 *
 * @author Alex Ruiz
 */
public class Bug263_separatorNotRespectedInJTree_Test extends RobotBasedTestCase {
	private JTreeFixture treeFixture;
	private MyWindow window;

	@Override
	protected void onSetUp() {
		window = MyWindow.createNew();
		treeFixture = new JTreeFixture(robot, window.tree);
		robot.showWindow(window);
	}

	@Test
	public void should_Use_Specified_Path_Separator() {
		treeFixture.replaceSeparator("*");
		assertThat(treeFixture.separator()).isEqualTo("*");
		treeFixture.selectPath("root*node1*node11*node111");
		Object selection = treeFixture.target().getSelectionPath().getLastPathComponent();
		assertThat(selection).isSameAs(window.nodeToSelect);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTree tree = new JTree();
		final MutableTreeNode nodeToSelect = node("node111");

		private MyWindow() {
			super(Bug263_separatorNotRespectedInJTree_Test.class);
			tree.setModel(new DefaultTreeModel(root()));
			tree.setPreferredSize(new Dimension(200, 200));
			add(tree);
		}

		private MutableTreeNode root() {
			return node("root", node("node1", node("node11", nodeToSelect, node("node112"))));
		}
	}
}
