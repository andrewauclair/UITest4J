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
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.swing.TreeNodeFactory.node;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-148" target="_blank">FEST-148</a>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FEST148_selectTreeCell_Test extends RobotBasedTestCase {
	private JTree tree;
	private JTreeDriver driver;

	@Override
	protected final void onSetUp() {
		MyWindow window = MyWindow.createNew();
		tree = window.tree;
		robot.showWindow(window);
		driver = new JTreeDriver(robot);
	}

	@Test
	public void should_Select_Path() {
		String pathToSelect = "root/node1/node11/node112/node1121/node11211/node112111/node1121111";
		driver.selectPath(tree, pathToSelect);
		driver.requireSelection(tree, array(pathToSelect));
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JTree tree = new JTree();
		final DefaultMutableTreeNode treeRoot;

		private MyWindow() {
			super(FEST148_selectTreeCell_Test.class);
			setLayout(new BorderLayout());
			treeRoot = root();
			tree.setModel(new DefaultTreeModel(treeRoot));
			setPreferredSize(new Dimension(100, 100));
			add(new JScrollPane(tree), CENTER);
		}

		private DefaultMutableTreeNode root() {
			return node(
					"root",
					node(
							"node1",
							node("node11",
									node("node111"),
									node("node112",
											node("node1121", node("node11211", node("node112111", node("node1121111")))))),
							node("node12")), node("node2"));
		}
	}
}
