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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Formatting#formatter(Class)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class Formatting_formatter_Test {
	private Class<JComboBox> type;
	private ComponentFormatter oldFormatter;
	private ComponentFormatter newFormatter;

	@BeforeEach
	void setUp() {
		type = JComboBox.class;
		oldFormatter = Formatting.formatter(type);
		newFormatter = new ComponentFormatterTemplate() {
			@Override
			protected String doFormat(@Nonnull Component c) {
				return null;
			}

			@Override
			public Class<? extends Component> targetType() {
				return type;
			}
		};
	}

	@AfterEach
	void tearDown() {
		Formatting.register(oldFormatter);
	}

	@Test
	void should_Replace_Existing_Formatter() {
		Formatting.register(newFormatter);
		assertThat(Formatting.formatter(type)).isSameAs(newFormatter);
	}
}
