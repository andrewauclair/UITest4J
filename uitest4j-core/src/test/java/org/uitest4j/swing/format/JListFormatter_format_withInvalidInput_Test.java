/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.format;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

/**
 * Tests for {@link JListFormatter#format(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JListFormatter_format_withInvalidInput_Test extends EDTSafeTestCase {
	@Test
	void should_Throw_Error_If_Component_Is_Not_JList() {
		assertThrows(IllegalArgumentException.class, () -> new JListFormatter().format(textField().createNew()));
	}
}
