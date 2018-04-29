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

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

import static org.uitest4j.swing.data.Index.atIndex;

/**
 * Tests for {@link JTabbedPaneDriver#requireTabEnabled(javax.swing.JTabbedPane, org.uitest4j.swing.data.Index)}.
 * 
 * @author William Bakker
 */
class JTabbedPaneDriver_requireTabEnabled_Test extends JTabbedPaneDriver_TestCase {
  @Test
  void should_Fail_If_Tab_Is_Disabled() {
    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireTabEnabled(tabbedPane, atIndex(2)), "property:'enabledAt'", "expected:<[tru]e> but was:<[fals]e>");
  }

  @Test
  void should_Pass_If_Tab_Is_Enabled() {
    driver.requireTabEnabled(tabbedPane, atIndex(0));
  }
}
