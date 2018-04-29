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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.timing.Condition;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.TreePath;

import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.driver.JTreeChildOfPathCountQuery.childCount;

/**
 * Verifies that the children of a node in a {@code JTree} are displayed.
 * 
 * @author Alex Ruiz
 */
class JTreeChildrenShowUpCondition extends Condition {
  private JTree tree;
  private TreePath path;

  static @Nonnull JTreeChildrenShowUpCondition untilChildrenShowUp(@Nonnull JTree tree, @Nonnull TreePath path) {
    return new JTreeChildrenShowUpCondition(tree, path);
  }

  private JTreeChildrenShowUpCondition(@Nonnull JTree tree, @Nonnull TreePath path) {
    super(concat(path.toString(), " to show"));
    this.tree = tree;
    this.path = path;
  }

  @Override
  @RunsInEDT
  public boolean test() {
    return childCount(tree, path) != 0;
  }

  @Override
  protected void done() {
    tree = null;
    path = null;
  }
}