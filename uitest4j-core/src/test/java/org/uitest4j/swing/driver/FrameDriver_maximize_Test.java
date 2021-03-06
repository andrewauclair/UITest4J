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

import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FrameDriver#maximize(java.awt.Frame)}.
 *
 * @author Alex Ruiz
 */
class FrameDriver_maximize_Test extends FrameDriver_TestCase {
	@Test
	void should_Maximize_Frame() {
		showWindow();
		driver.maximize(window);
		int frameState = frameState() & MAXIMIZED_BOTH;
		assertThat(frameState).isEqualTo(MAXIMIZED_BOTH);
	}

	@Test
	void should_Throw_Error_If_Frame_Is_Disabled() {
		disableWindow();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.maximize(window));
	}

	@Test
	void should_Throw_Error_If_Frame_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.maximize(window));
	}
}
