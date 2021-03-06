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
 * Tests for {@link JInternalFrameDriver#requireTitle(JInternalFrame, String)}.
 *
 * @author Christian Rösch
 */
class JInternalFrameDriver_requireTitle_Test extends JInternalFrameDriver_TestCase {
	@Test
	void should_Pass_If_Frame_Has_Expected_Title() {
		driver.requireTitle(internalFrame, internalFrame.getTitle());
	}

	@Test
	void should_Fail_If_Frame_Does_Not_Have_Expected_Title() {
		String correctTitle = internalFrame.getTitle();
		ExpectedException.assertOpenTest4jError(() -> driver.requireTitle(internalFrame, "incorrect title"), "Expected title of 'TestInternalFrame' to be 'incorrect title' but was '" + correctTitle + "'", "incorrect title", correctTitle);
	}
}
