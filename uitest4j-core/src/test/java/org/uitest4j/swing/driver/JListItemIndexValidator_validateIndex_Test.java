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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.ExpectedException;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JListItemPreconditions#checkIndexInBounds(JList, int)}.
 *
 * @author Alex Ruiz
 */
class JListItemIndexValidator_validateIndex_Test extends JListItemIndexValidator_TestCase {
	@Test
	void should_Pass_If_Index_Is_Valid() {
		validateIndex(0);
	}

	@Test
	void should_Throw_Error_If_Index_Is_Negative() {
		ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> validateIndex(-1), "Item index (-1) should be between [0] and [2] (inclusive)");
	}

	@Test
	void should_Throw_Error_If_Index_Is_Greater_Than_Index_Of_Last_Item() {
		ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> validateIndex(3), "Item index (3) should be between [0] and [2] (inclusive)");
	}

	@RunsInEDT
	private void validateIndex(int index) {
		validateIndex(list, index);
	}

	@RunsInEDT
	private static void validateIndex(final JList list, final int index) {
		execute(() -> JListItemPreconditions.checkIndexInBounds(list, index));
	}
}
