/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link JLabelTextReader#checkContainsText(JLabel, String)}.
 *
 * @author Alex Ruiz
 */
class JLabelTextReader_checkContainsText_Test extends EDTSafeTestCase {
	private JLabel label;
	private JLabelTextReader reader;

	@BeforeEach
	void setUp() {
		label = mock(JLabel.class);
		reader = new JLabelTextReader();
	}

	@Test
	void should_Return_False_If_Text_In_JLabel_Is_Null() {
		when(label.getText()).thenReturn(null);
		assertThat(reader.checkContainsText(label, "Yoda")).isFalse();
	}

	@Test
	void should_Return_False_If_Text_In_JLabel_Does_Not_Contain_Given_String() {
		when(label.getText()).thenReturn("Leia");
		assertThat(reader.checkContainsText(label, "Yoda")).isFalse();
	}

	@Test
	void should_Return_True_If_Text_In_JLabel_Contains_Given_String() {
		when(label.getText()).thenReturn("Yoda");
		assertThat(reader.checkContainsText(label, "Yo")).isTrue();
	}
}
