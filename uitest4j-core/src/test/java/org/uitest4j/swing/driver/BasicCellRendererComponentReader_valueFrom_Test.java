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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JButtons.button;
import static org.uitest4j.swing.test.builder.JLabels.label;

/**
 * Tests for {@link BasicCellRendererReader#valueFrom(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
class BasicCellRendererComponentReader_valueFrom_Test extends EDTSafeTestCase {
	private BasicCellRendererReader reader;

	@BeforeEach
	void setUp() {
		reader = new BasicCellRendererReader();
	}

	@Test
	void should_Return_Value_From_JLabel() {
		JLabel label = label().withText("Hello").createNew();
		assertThat(reader.valueFrom(label)).isEqualTo("Hello");
	}

	@Test
	void should_Return_Null_If_Component_Is_Not_JLabel() {
		JButton button = button().withText("Hello").createNew();
		assertThat(reader.valueFrom(button)).isNull();
	}
}
