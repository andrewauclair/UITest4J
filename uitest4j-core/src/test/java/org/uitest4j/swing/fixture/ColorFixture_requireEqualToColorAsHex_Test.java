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

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static org.uitest4j.swing.fixture.ColorHexCodes.BLACK_HEX_CODE;
import static org.uitest4j.swing.fixture.ColorHexCodes.BLUE_HEX_CODE;

/**
 * Tests for {@link ColorFixture#requireEqualTo(String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ColorFixture_requireEqualToColorAsHex_Test {
	@Test
	void should_Pass_If_Color_Is_Equal_To_Hex_Value() {
		ColorFixture fixture = new ColorFixture(BLUE);
		fixture.requireEqualTo(BLUE_HEX_CODE);
	}

	@Test
	void should_Fail_If_Color_Is_Not_Equal_To_Hex_Value() {
		ColorFixture fixture = new ColorFixture(BLUE);
		ExpectedException.assertOpenTest4jError(() -> fixture.requireEqualTo(BLACK_HEX_CODE), "Expected " + BLACK.toString() + " but was " + BLUE.toString(), BLACK, BLUE);
	}

	@Test
	void should_Fail_Showing_Description_If_Color_Is_Not_Equal_To_Hex_Value() {
		ColorFixture fixture = new ColorFixture(BLUE, "test");
		ExpectedException.assertOpenTest4jError(() -> fixture.requireEqualTo(BLACK_HEX_CODE), "[test] Expected " + BLACK.toString() + " but was " + BLUE.toString(), BLACK, BLUE);
	}
}
