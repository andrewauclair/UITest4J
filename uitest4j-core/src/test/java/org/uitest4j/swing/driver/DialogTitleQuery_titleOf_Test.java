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
 * Verifies that {@link DialogTitleQuery#titleOf(Dialog)} returns the title of the dialog.
 *
 * @author Andrew Auclair
 * @see DialogTitleQuery#titleOf(Dialog)
 */
class DialogTitleQuery_titleOf_Test extends DialogDriver_TestCase {
	private static final String CORRECT_TITLE = DialogTitleQuery_titleOf_Test.class.getSimpleName();

	@Test
	void returns_title_of_frame() {
		assertThat(DialogTitleQuery.titleOf(dialog)).isEqualTo(CORRECT_TITLE);
	}
}
