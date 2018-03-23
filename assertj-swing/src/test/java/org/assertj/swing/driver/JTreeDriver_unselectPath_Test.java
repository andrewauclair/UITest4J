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
package org.assertj.swing.driver;

import org.assertj.swing.exception.LocationUnavailableException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTreeDriver#unselectPath(javax.swing.JTree, String)}.
 *
 * @author Christian Rösch
 */
public class JTreeDriver_unselectPath_Test extends JTreeDriver_selectCell_TestCase {
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  void should_Throw_Error_If_Path_Not_Found() {
    showWindow();
    ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> driver.unselectPath(tree, "another"), "Unable to find path 'another'");
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Disabled() {
    disableTree();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.unselectPath(tree, "root/branch1"));
  }

  @Test
  void should_Not_Do_Anything_If_Cell_Is_Already_Unselected() {
    showWindow();
    clearTreeSelection();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tree);
    driver.unselectPath(tree, "root/branch1/branch1.1/branch1.1.1");
    recorder.wasNotClicked();
    requireNoSelection();
  }

  @Test
  void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.unselectPath(tree, "root/branch1"));
  }
}
