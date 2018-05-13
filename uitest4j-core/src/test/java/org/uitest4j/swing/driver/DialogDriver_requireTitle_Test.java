/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

import java.awt.*;

/**
 * Tests for {@link DialogDriver#requireTitle(Dialog, String)}.
 *
 * @author Andrew Auclair
 */
class DialogDriver_requireTitle_Test extends DialogDriver_TestCase {
	private static final String CORRECT_TITLE = DialogDriver_requireTitle_Test.class.getSimpleName();

	@Test
	void should_Pass_If_Frame_Has_Expected_Title() {
		driver.requireTitle(dialog, CORRECT_TITLE);
	}

	@Test
	void should_Fail_If_Frame_Does_Not_Have_Expected_Title() {
		ExpectedException.assertOpenTest4jError(() -> driver.requireTitle(dialog, "incorrect title"), "Expected title of 'TestDialog' to be 'incorrect title' but was '" + CORRECT_TITLE + "'", "incorrect title", CORRECT_TITLE);
	}
}
