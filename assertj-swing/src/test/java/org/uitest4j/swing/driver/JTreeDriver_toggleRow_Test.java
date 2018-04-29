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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTreeDriver#toggleRow(javax.swing.JTree, int)}.
 * 
 * @author Alex Ruiz
 */
class JTreeDriver_toggleRow_Test extends JTreeDriver_toggleCell_TestCase {
  @Test
  void shouldToggleNodeByRow() {
    showWindow();
    requireRowCollapsed(5);
    driver.toggleRow(tree, 5);
    requireRowExpanded(5);
    driver.toggleRow(tree, 5);
    requireRowCollapsed(5);
    driver.toggleRow(tree, 5);
    requireRowExpanded(5);
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Disabled() {
    disableTree();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.toggleRow(tree, 0));
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.toggleRow(tree, 0));
  }
}
