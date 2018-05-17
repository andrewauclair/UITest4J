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
import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.swing.test.ExpectedException;

import javax.swing.text.JTextComponent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.driver.JTextComponentTextQuery.textOf;

/**
 * Tests for {@link JSpinnerDriver#enterText(javax.swing.JSpinner, String)}.
 *
 * @author Alex Ruiz
 */
class JSpinnerDriver_enterText_Test extends JSpinnerDriver_TestCase {
	@Test
	void should_Throw_Error_If_JTextComponentEditor_Not_Found() {
		setJLabelAsEditor();
		showWindow();
		assertThrows(ActionFailedException.class, () -> driver.enterText(spinner, "hello"));
	}

	@Test
	void should_Enter_Text() {
		showWindow();
		updateValue("Frodo");
		driver.enterText(spinner, "Gandalf");
		JTextComponent editor = driver.editor(spinner);
		assertThat(textOf(editor)).isEqualTo("Gandalf");
		assertThatValueIs("Frodo");
	}

	@Test
	void should_Throw_Error_If_JSpinner_Is_Disabled() {
		disableSpinner();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.enterText(spinner, "Gandalf"));
	}

	@Test
	void should_Throw_Error_If_JSpinner_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.enterText(spinner, "Gandalf"));
	}
}
