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

/**
 * Tests for {@link JScrollBarDriver#scrollTo(javax.swing.JScrollBar, int)}.
 *
 * @author Alex Ruiz
 */
class JScrollBarDriver_scrollTo_Test extends JScrollBarDriver_TestCase {
	@Test
	void should_Scroll_To_Given_Position() {
		showWindow();
		driver.scrollTo(scrollBar, 68);
		assertThatScrollBarValueIs(68);
	}

	@Test
	void should_Throw_Error_If_JScrollBar_Is_Disabled() {
		disableScrollBar();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.scrollTo(scrollBar, 68));
	}

	@Test
	void should_Throw_Error_If_JScrollBar_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.scrollTo(scrollBar, 68));
	}

	@Test
	void should_Throw_Error_If_Position_Is_Less_Than_Minimum() {
		ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.scrollTo(scrollBar, 0), "Position <0> is not within the JScrollBar bounds of <10> and <80>");
	}

	@Test
	void should_Throw_Error_If_Position_Is_Greater_Than_Maximum() {
		ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.scrollTo(scrollBar, 90), "Position <90> is not within the JScrollBar bounds of <10> and <80>");
	}
}
