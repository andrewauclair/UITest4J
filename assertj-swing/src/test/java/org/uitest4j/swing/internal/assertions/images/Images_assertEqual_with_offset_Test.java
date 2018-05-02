/**
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.internal.assertions.ImagesBaseTest;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.BLUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.uitest4j.swing.assertions.error.ShouldBeEqualImages.shouldBeEqualImages;
import static org.uitest4j.swing.test.awt.AwtTestData.*;

/**
 * Tests for <code>{@link Images#assertEqual(AssertionInfo, BufferedImage, BufferedImage, int)}</code>.
 *
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
public class Images_assertEqual_with_offset_Test extends ImagesBaseTest {

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		offset = 5;
	}

	@Test
	void should_Pass_If_Images_Are_Equal() {
		Color similarBlue = new Color(0, 0, 250);
		images.assertEqual(actual, newImage(5, 5, similarBlue), offset);
	}

	@Test
	void should_Pass_If_Images_Are_Same() {
		images.assertEqual(actual, actual, offset);
	}

	@Test
	void should_Pass_If_Both_Images_Are_Null() {
		images.assertEqual(null, null, offset);
	}

	@Test
	void should_Fail_If_Actual_Is_Null_And_Expected_Is_Not() {
		assertThrows(AssertionFailedError.class, () -> images.assertEqual(null, fivePixelBlueImage(), offset));
	}

	@Test
	void should_Fail_If_Expected_Is_Null_And_Actual_Is_Not() {
		assertThrows(AssertionFailedError.class, () -> images.assertEqual(actual, null, offset));
	}

	@Test
	void should_Fail_If_Images_Have_Different_Size() {
		AssertionInfo info = someInfo();
		BufferedImage expected = newImage(6, 6, BLUE);
		assertThrows(AssertionFailedError.class, () -> images.assertEqual(actual, expected, offset));
	}

	@Test
	void should_Fail_If_Images_Have_Same_Size_But_Different_Color() {
		AssertionInfo info = someInfo();
		BufferedImage expected = fivePixelYellowImage();
		assertThrows(AssertionFailedError.class, () -> images.assertEqual(actual, expected, offset));
	}
}
