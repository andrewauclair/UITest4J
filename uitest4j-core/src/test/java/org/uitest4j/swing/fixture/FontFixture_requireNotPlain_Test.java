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
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link FontFixture#requireNotPlain()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class FontFixture_requireNotPlain_Test extends FontFixture_TestCase {
	@Test
	void should_Pass_If_Font_Is_Not_Plain() {
		FontFixture fixture = new FontFixture(boldFont());
		fixture.requireNotPlain();
	}

	@Test
	void should_Fail_If_Font_Is_Plain() {
		ExpectedException.assertOpenTest4jError(() -> fixture().requireNotPlain(), "Expected font to not be plain");
	}

	@Test
	void should_Fail_Showing_Description_If_Font_Is_Plain() {
		ExpectedException.assertOpenTest4jError(() -> fixture().requireNotPlain(), "Expected font to not be plain");
	}
}
