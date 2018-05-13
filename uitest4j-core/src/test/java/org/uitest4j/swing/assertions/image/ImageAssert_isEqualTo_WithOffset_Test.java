/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.assertions.image;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.assertions.ImageAssert;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.assertions.Assertions.assertThat;
import static org.uitest4j.swing.test.awt.AwtTestData.fivePixelBlueImage;
import static org.uitest4j.swing.test.awt.AwtTestData.fivePixelYellowImage;

/**
 * Tests for <code>{@link ImageAssert#isEqualTo(BufferedImage, Offset)}</code>.
 *
 * @author Yvonne Wang
 */
public class ImageAssert_isEqualTo_WithOffset_Test {
	@Test
	void calls_isEqualTo_with_offset_exception() {
		assertThrows(AssertionFailedError.class, () -> assertThat(fivePixelBlueImage()).isEqualTo(fivePixelYellowImage(), 20));
	}
}
