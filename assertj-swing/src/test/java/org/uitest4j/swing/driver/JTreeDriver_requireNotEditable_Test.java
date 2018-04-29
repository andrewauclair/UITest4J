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

import static org.uitest4j.swing.driver.JTreeSetEditableTask.setEditable;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JTreeDriver#requireNotEditable(javax.swing.JTree)}.
 * 
 * @author Alex Ruiz
 */
class JTreeDriver_requireNotEditable_Test extends JTreeDriver_TestCase {
  @Test
  void should_Pass_If_JTree_Is_Not_Editable() {
    setEditable(tree, false);
    robot.waitForIdle();
    driver.requireNotEditable(tree);
  }

  @Test
  void should_Fail_If_JTree_Is_Editable() {
    setEditable(tree, true);
    robot.waitForIdle();
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireNotEditable(tree), "property:'editable'", "expected:<[fals]e> but was:<[tru]e>");
  }
}
