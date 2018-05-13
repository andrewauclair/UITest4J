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

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.driver.AbstractButtonTextQuery.textOf;

/**
 * Tests for {@link JFileChooserDriver#cancelButton(javax.swing.JFileChooser)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JFileChooserDriver_cancelButton_Test extends JFileChooserDriver_TestCase {
	@Test
	void should_Find_Cancel_Button() {
		showWindow();
		JButton cancelButton = driver.cancelButton(fileChooser);
		assertThat(cancelButton).isNotNull();
		assertThat(textOf(cancelButton)).isEqualTo(cancelButtonText());
	}

	private String cancelButtonText() {
		return UIManager.getString("FileChooser.cancelButtonText");
	}
}
