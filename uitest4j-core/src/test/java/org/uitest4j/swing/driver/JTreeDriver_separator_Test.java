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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.core.TestRobots.singletonRobotMock;

/**
 * Tests for {@link JTreeDriver#replaceSeparator(String)}.
 *
 * @author Alex Ruiz
 */
class JTreeDriver_separator_Test {
	private JTreeDriver driver;

	@BeforeEach
	void setUp() {
		driver = new JTreeDriver(singletonRobotMock());
	}

	@Test
	void shouldThrowErrorIfSeparatorIsNull() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> driver.replaceSeparator(null));
	}

	@Test
	void shouldSetPathSeparator() {
		driver.replaceSeparator("|");
		assertThat(driver.separator()).isEqualTo("|");
	}
}
