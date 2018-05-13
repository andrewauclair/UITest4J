/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link JComboBoxDriver#requireItemCount(javax.swing.JComboBox, int)}.
 *
 * @author Alex Ruiz
 */
class JComboBoxDriver_requireItemCount_Test extends JComboBoxDriver_TestCase {
	@Test
	void should_Pass_If_JList_Has_Expected_Item_Count() {
		driver.requireItemCount(comboBox, 3);
	}

	@Test
	void should_Fail_If_JList_Does_Not_Have_Expected_Item_Count() {
		ExpectedException.assertOpenTest4jError(() -> driver.requireItemCount(comboBox, 6), "Expected item count of 'TestComboBox' to be '6' but was '3'", 6, 3);
	}
}
