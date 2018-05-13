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

import static org.uitest4j.swing.driver.JTreeSetEditableTask.setEditable;

/**
 * Tests for {@link JTreeDriver#requireNotEditable(javax.swing.JTree)}.
 *
 * @author Alex Ruiz
 */
class JTreeDriver_requireNotEditable_Test extends JTreeDriver_TestCase {
	@Test
	void should_Fail_If_JTree_Is_Editable() {
		setEditable(tree, true);
		robot.waitForIdle();
		ExpectedException.assertOpenTest4jError(() -> driver.requireNotEditable(tree), "Expected 'TestTree' to not be editable");
	}

	@Test
	void should_Pass_If_JTree_Is_Not_Editable() {
		setEditable(tree, false);
		robot.waitForIdle();
		driver.requireNotEditable(tree);
	}
}
