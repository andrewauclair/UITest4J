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
package org.uitest4j.javafx.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

/**
 * @author Andrew Auclair
 */
class ButtonBaseDriver_requireTextAsString_Test extends ButtonBaseDriver_TestCase {
	@Test
	void passes_when_text_matches_string() {
		driver.requireText(button, "Hi");
	}
	
	@Test
	void fails_when_text_does_not_match_string() {
		ExpectedException.assertOpenTest4jError(() -> driver.requireText(button, "Bye"), "Expected text of 'TestButton' to be 'Bye' but was 'Hi'", "Bye", "Hi");
	}
}
