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
import org.uitest4j.swing.core.MouseClickInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.core.MouseButton.*;

/**
 * Tests for {@link MouseClickInfo}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class MouseClickInfoTest {
	@Test
	void shouldThrowErrorIfMouseButtonIsNull() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> MouseClickInfo.button(null));
	}

	@Test
	void shouldCreateLeftButtonClickOneTime() {
		MouseClickInfo button = MouseClickInfo.leftButton();
		assertThat(button.button()).isEqualTo(LEFT_BUTTON);
		assertThat(button.times()).isEqualTo(1);
	}

	@Test
	void shouldCreateMiddleButtonClickOneTime() {
		MouseClickInfo button = MouseClickInfo.middleButton();
		assertThat(button.button()).isEqualTo(MIDDLE_BUTTON);
		assertThat(button.times()).isEqualTo(1);
	}

	@Test
	void shouldCreateRightButtonClickOneTime() {
		MouseClickInfo button = MouseClickInfo.rightButton();
		assertThat(button.button()).isEqualTo(RIGHT_BUTTON);
		assertThat(button.times()).isEqualTo(1);
	}

	@Test
	void shouldIncludeButtonAndTimesPressedInToString() {
		MouseClickInfo button = MouseClickInfo.rightButton();
		assertThat(button.toString()).contains("button=RIGHT_BUTTON").contains("times=1");
	}
}
