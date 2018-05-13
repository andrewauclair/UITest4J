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

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

/**
 * Tests for {@link ColorFixture#requireNotEqualTo(java.awt.Color)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ColorFixture_requireNotEqualToColor_Test {
	@Test
	void should_Pass_If_Colors_Are_Not_Equal() {
		ColorFixture fixture = new ColorFixture(BLUE);
		fixture.requireNotEqualTo(RED);
	}

	@Test
	void should_Fail_If_Colors_Are_Equal() {
		ColorFixture fixture = new ColorFixture(BLUE);
		ExpectedException.assertOpenTest4jError(() -> fixture.requireNotEqualTo(BLUE), "Expected color to not equal " + BLUE.toString());
	}

	@Test
	void should_Fail_Showing_Description_If_Colors_Are_Equal() {
		ColorFixture fixture = new ColorFixture(BLUE, "test");
		ExpectedException.assertOpenTest4jError(() -> fixture.requireNotEqualTo(BLUE), "[test] Expected color to not equal " + BLUE.toString());
	}
}
