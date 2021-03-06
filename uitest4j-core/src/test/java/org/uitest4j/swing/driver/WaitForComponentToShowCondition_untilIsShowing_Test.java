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
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link WaitForComponentToShowCondition#test()}.
 *
 * @author Yvonne Wang
 */
class WaitForComponentToShowCondition_untilIsShowing_Test extends EDTSafeTestCase {
	@Test
	void shouldThrowError_If_Component_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> WaitForComponentToShowCondition.untilIsShowing(null));
	}
}
