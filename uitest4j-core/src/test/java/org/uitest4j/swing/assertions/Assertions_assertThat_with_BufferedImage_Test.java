/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.assertions;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.uitest4j.swing.assertions.Assertions.assertThat;
import static org.uitest4j.swing.test.awt.AwtTestData.fivePixelBlueImage;

/**
 * Tests for <code>{@link AwtAssertions#assertThat(BufferedImage)}</code>.
 *
 * @author Alex Ruiz
 */
class Assertions_assertThat_with_BufferedImage_Test {

	@Test
	void should_Create_Assert() {
		ImageAssert assertions = assertThat(fivePixelBlueImage());
		assertNotNull(assertions);
	}

	@Test
	void should_Pass_Actual() {
		BufferedImage actual = fivePixelBlueImage();
		ImageAssert assertions = assertThat(actual);
		assertSame(actual, assertions.actual);
	}
}
