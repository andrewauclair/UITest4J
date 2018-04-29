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

/**
 * Tests for {@link JTabbedPaneLocation#indexOf(javax.swing.JTabbedPane, String)}.
 * 
 * @author Yvonne Wang
 */
class JTabbedPaneLocation_validateIndex_Test extends JTabbedPaneLocation_TestCase {
  @Test
  void should_Pass_If_Index_If_Valid() {
    location.checkIndexInBounds(tabbedPane, 0);
  }

  @Test
  void should_Fail_If_Index_Is_Negative() {
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> location.checkIndexInBounds(tabbedPane, -1), "Index <-1> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
  }

  @Test
  void should_Fail_If_Index_Is_Out_Of_Bounds() {
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> location.checkIndexInBounds(tabbedPane, 2), "Index <2> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
  }
}
