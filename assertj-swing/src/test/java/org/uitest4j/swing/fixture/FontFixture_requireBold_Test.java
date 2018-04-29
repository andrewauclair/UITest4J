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
package org.uitest4j.swing.fixture;

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FontFixture#requireBold()}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class FontFixture_requireBold_Test extends FontFixture_TestCase {
  @Test
  void should_Pass_If_Font_Is_Bold() {
    FontFixture fixture = new FontFixture(boldFont());
    fixture.requireBold();
  }

  @Test
  void should_Fail_If_Font_Is_Not_Bold() {
    ExpectedException.assertContainsMessage(AssertionError.class, () ->fixture().requireBold(), "[bold] expected:<[tru]e> but was:<[fals]e>");
  }

  @Test
  void should_Fail_Showing_Description_If_Font_Is_Not_Bold() {
    FontFixture fixture = new FontFixture(font(), "test");
    ExpectedException.assertContainsMessage(AssertionError.class, () -> fixture.requireBold(), "[test - bold] expected:<[tru]e> but was:<[fals]e>");
  }
}
