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
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link JTextComponentDriver#requireText(javax.swing.text.JTextComponent, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_requireTextAsString_Test extends JTextComponentDriver_TestCase {
	@Test
	public void should_Pass_If_Has_Expected_Text() {
		setTextFieldText("Hi");
		driver.requireText(textField, "Hi");
	}

	@Test
	public void should_Fail_If_Does_Not_Have_Expected_Text() {
		setTextFieldText("Hi");
		ExpectedException.assertOpenTest4jError(() -> driver.requireText(textField, "Bye"), "Expected text of 'TestTextField' to be 'Bye' but was 'Hi'", "Bye", "Hi");
	}
}
