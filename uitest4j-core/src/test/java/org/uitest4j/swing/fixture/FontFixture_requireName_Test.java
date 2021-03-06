/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link FontFixture#requireName(String)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class FontFixture_requireName_Test extends FontFixture_TestCase {
	@Test
	void should_Pass_If_Name_Is_Equal_To_Expected() {
		fixture().requireName("SansSerif");
	}

	@Test
	void should_Fail_If_Name_Is_Not_Equal_To_Expected() {
		ExpectedException.assertOpenTest4jError(() -> fixture().requireName("Monospace"), "Expected font name to be 'Monospace' but was 'SansSerif'", "Monospace", "SansSerif");
	}

	@Test
	void should_Fail_Showing_Description_If_Name_Is_Not_Equal_To_Expected() {
		FontFixture fixture = new FontFixture(font(), "test");
		ExpectedException.assertOpenTest4jError(() -> fixture.requireName("Monospace"), "[test] Expected font name to be 'Monospace' but was 'SansSerif'", "Monospace", "SansSerif");
	}
}
