/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link JTabbedPaneDriver#requireTabDisabled(javax.swing.JTabbedPane, int)}.
 *
 * @author William Bakker
 */
class JTabbedPaneDriver_requireTabDisabled_Test extends JTabbedPaneDriver_TestCase {
	@Test
	void should_Fail_If_Tab_Is_Enabled() {
		ExpectedException.assertOpenTest4jError(() -> driver.requireTabDisabled(tabbedPane, 0),
				"Expected tab at index 0 of 'TestTabbedPane' to be disabled");
	}

	@Test
	void should_Pass_If_Tab_Is_Disabled() {
		driver.requireTabDisabled(tabbedPane, 2);
	}
}
