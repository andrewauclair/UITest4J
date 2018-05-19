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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestTree;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.swing.TreeNodeFactory.node;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-277" target="_blank">FEST-277</a>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FEST227_expandJTreePathMayCauseNPE_Test extends SwingRobotBasedTestCase {
	private FrameFixture frameFixture;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		frameFixture = new FrameFixture(robot, window);
		frameFixture.show();
	}

	@Test
	public void should_Expanding_Nodes_Should_Not_Cause_NPE() {
		frameFixture.tree().expandPath("root");
		frameFixture.tree().expandPath("root/a");
		frameFixture.tree().expandPath("root/a/b");
		frameFixture.tree().expandPath("root/a/b/c");
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final TestTree tree = new TestTree(nodes());

		private static TreeModel nodes() {
			MutableTreeNode root = node("root", node("a", node("b", node("c"))));
			return new DefaultTreeModel(root);
		}

		private MyWindow() {
			super(FEST227_expandJTreePathMayCauseNPE_Test.class);
			add(decorate(tree));
			tree.collapseRow(0);
			setPreferredSize(new Dimension(600, 400));
		}

		private static Component decorate(JTree tree) {
			JScrollPane scrollPane = new JScrollPane(tree);
			scrollPane.setPreferredSize(new Dimension(200, 100));
			return scrollPane;
		}
	}
}
