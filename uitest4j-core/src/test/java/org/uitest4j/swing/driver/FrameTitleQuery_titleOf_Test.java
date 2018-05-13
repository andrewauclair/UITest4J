/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2018 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that {@link FrameTitleQuery#titleOf(Frame)} returns the title of the frame.
 *
 * @author Andrew Auclair
 * @see FrameTitleQuery#titleOf(Frame)
 */
class FrameTitleQuery_titleOf_Test extends FrameDriver_TestCase {
	private static final String CORRECT_TITLE = FrameTitleQuery_titleOf_Test.class.getSimpleName();

	@Test
	void returns_title_of_frame_and_executes_in_edt() {
		assertThat(FrameTitleQuery.titleOf(window)).isEqualTo(CORRECT_TITLE);
	}
}
