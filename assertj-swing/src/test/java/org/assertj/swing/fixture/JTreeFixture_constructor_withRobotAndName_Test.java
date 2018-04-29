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

import org.uitest4j.swing.exception.ComponentLookupException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTree;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.swing.TreeNodeFactory.node;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTreeFixture#JTreeFixture(org.uitest4j.swing.core.Robot, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  void should_Lookup_Showing_JTree_By_Name() {
    robot.showWindow(window);
    JTreeFixture fixture = new JTreeFixture(robot, "tree");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.tree);
  }

  @Test
  void should_Throw_Error_If_JTree_With_Matching_Name_Is_Not_Showing() {
    assertThrows(ComponentLookupException.class, () -> new JTreeFixture(robot, "tree"));
  }

  @Test
  void should_Throw_Error_If_A_JTree_With_Matching_Name_Is_Not_Found() {
    assertThrows(ComponentLookupException.class, () -> new JTreeFixture(robot, "other"));
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final TestTree tree = new TestTree(nodes());

    private static TreeModel nodes() {
      MutableTreeNode root = node("root",
          node("branch1", node("branch1.1", node("branch1.1.1"), node("branch1.1.2")), node("branch1.2")));
      return new DefaultTreeModel(root);
    }

    private MyWindow() {
      super(JTreeFixture_constructor_withRobotAndName_Test.class);
      tree.setName("tree");
      add(decorate(tree));
    }

    @RunsInCurrentThread
    private static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(new Dimension(200, 100));
      return scrollPane;
    }
  }
}
