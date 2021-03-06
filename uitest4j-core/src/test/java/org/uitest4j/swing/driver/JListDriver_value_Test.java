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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JListDriver#value(javax.swing.JList, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_value_Test extends JListDriver_TestCase {
	@Test
	void should_Return_Text_Of_Item() {
		Object text = driver.value(list, 0);
		assertThat(text).isEqualTo("one");
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Throw_Error_If_Index_Is_Out_Of_Bounds() {
		ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> driver.value(list, 6), "Item index (6) should be between [0] and [2] (inclusive)");
	}
}
