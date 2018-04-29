/**
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
package org.assertj.swing.fixture;

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTree;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.*;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.swing.TreeNodeFactory.node;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-119" target="_blank">FEST-119</a>.
 * 
 * @author Alex Ruiz
 */
public class FEST119_cannotSelectCellInJTreeIfRootIsInvisible_Test extends RobotBasedTestCase {
    private FrameFixture frameFixture;

  @Override
  protected void onSetUp() {
      MyWindow window = MyWindow.createNew();
    frameFixture = new FrameFixture(robot, window);
    frameFixture.show();
  }

  @Test
  public void should_Select_Cell_When_Root_In_JTree_Is_Invisible() {
    JTreeFixture treeFixture = frameFixture.tree();
    treeFixture.selectPath("branch1");
    treeFixture.requireSelection(0);
    treeFixture.selectPath("branch1/branch1.1");
    treeFixture.requireSelection(1);
    treeFixture.selectRow(0);
    treeFixture.requireSelection("branch1");
    treeFixture.selectRow(1);
    treeFixture.requireSelection("branch1/branch1.1");
  }

  private static class MyWindow extends TestWindow {
    private static final Dimension TREE_SIZE = new Dimension(200, 100);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final TestTree tree = new TestTree(nodes());

    private static TreeModel nodes() {
      MutableTreeNode root = node("root",
          node("branch1", node("branch1.1", node("branch1.1.1"), node("branch1.1.2")), node("branch1.2")),
          node("branch2"));
      return new DefaultTreeModel(root);
    }

    private MyWindow() {
      super(FEST119_cannotSelectCellInJTreeIfRootIsInvisible_Test.class);
      tree.setRootVisible(false);
      add(decorate(tree));
      setPreferredSize(new Dimension(600, 400));
    }

    private static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }
  }
}
