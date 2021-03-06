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
import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JTabbedPaneLocation#indexOf(javax.swing.JTabbedPane, String)}.
 *
 * @author Yvonne Wang
 */
class JTabbedPaneLocation_indexOf_Test extends JTabbedPaneLocation_TestCase {
	@Test
	void should_Return_Index_Of_Tab_With_Matching_Title() {
		int index = location.indexOf(tabbedPane, "two");
		assertThat(index).isEqualTo(1);
	}

	@Test
	void should_Throw_Error_If_A_Matching_Tab_Cannot_Be_Found() {
		ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> location.indexOf(tabbedPane, "three"), "Unable to find a tab with title matching value 'three'");
	}
}
