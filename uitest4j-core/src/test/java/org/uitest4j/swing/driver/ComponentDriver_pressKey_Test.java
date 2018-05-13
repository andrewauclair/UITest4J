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
import org.uitest4j.swing.test.ExpectedException;

import static java.awt.event.KeyEvent.VK_A;

/**
 * Tests for {@link ComponentDriver#pressKey(java.awt.Component, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_pressKey_Test extends ComponentDriver_TestCase {
	@Test
	void should_Throw_Error_If_Component_Is_Disabled() {
		disableTextField();
		try {
			ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.pressKey(window.textField, VK_A));
		}
		finally {
			assertThatTextFieldIsEmpty();
		}
	}

	@Test
	void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
		assertThatTextFieldIsEmpty();
		try {
			ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.pressKey(window.textField, VK_A));
		}
		finally {
			assertThatTextFieldIsEmpty();
		}
	}
}
