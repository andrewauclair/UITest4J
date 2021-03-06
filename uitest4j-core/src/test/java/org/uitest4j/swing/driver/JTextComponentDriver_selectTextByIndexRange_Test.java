/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link JTextComponentDriver#selectText(javax.swing.text.JTextComponent, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTextComponentDriver_selectTextByIndexRange_Test extends JTextComponentDriver_TestCase {
	@Test
	void should_Select_Text_Range() {
		showWindow();
		driver.selectText(textField, 8, 14);
		requireSelectedTextInTextField("a test");
	}

	@Test
	void should_Throw_Error_If_JTextComponent_Is_Disabled() {
		disableTextField();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectText(textField, 8, 14));
	}

	@Test
	void should_Throw_Error_If_JTextComponent_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectText(textField, 8, 14));
	}

	@Test
	void should_Throw_Error_If_Indices_Are_Out_Of_Bounds() {
		showWindow();
		ExpectedException.assertContainsMessage(ActionFailedException.class, () -> driver.selectText(textField, 20, 22), "Unable to get location for index <20> in javax.swing.JTextField");
	}
}
