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
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.internal.assertions.images;

import org.assertj.core.api.AssertionInfo;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.internal.assertions.Images;
import org.uitest4j.swing.internal.assertions.ImagesBaseTest;

import java.awt.image.BufferedImage;

import static java.awt.Color.BLUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.test.awt.AwtTestData.*;

/**
 * Tests for <code>{@link Images#assertNotEqual(BufferedImage, BufferedImage)}</code>.
 *
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
class Images_assertNotEqual_Test extends ImagesBaseTest {

	@Test
	void should_Pass_If_Actual_Is_Null_And_Expected_Is_Not() {
		images.assertNotEqual(null, fivePixelBlueImage());
	}

	@Test
	void should_Pass_If_Expected_Is_Null_And_Actual_Is_Not() {
		images.assertNotEqual(actual, null);
	}

	@Test
	void should_Pass_If_Images_Have_Different_Size() {
		images.assertNotEqual(actual, newImage(3, 3, BLUE));
	}

	@Test
	void should_Pass_If_Images_Have_Different_Color() {
		images.assertNotEqual(actual, fivePixelYellowImage());
	}

	@Test
	void should_Fail_If_Images_Are_Equal() {
		AssertionInfo info = someInfo();
		BufferedImage other = newImage(5, 5, BLUE);
		assertThrows(AssertionFailedError.class, () -> images.assertNotEqual(actual, other));
	}

	@Test
	void should_Fail_If_Images_Are_Same() {
		AssertionInfo info = someInfo();
		assertThrows(AssertionFailedError.class, () -> images.assertNotEqual(actual, actual));
	}
}
