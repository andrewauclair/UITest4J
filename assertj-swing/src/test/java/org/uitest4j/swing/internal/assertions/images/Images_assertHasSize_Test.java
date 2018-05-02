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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.internal.assertions.Images;
import org.uitest4j.swing.internal.assertions.ImagesBaseTest;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.BLUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.test.awt.AwtTestData.newImage;

/**
 * Tests for <code>{@link Images#assertHasSize(BufferedImage, Dimension)}</code>.
 *
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
public class Images_assertHasSize_Test extends ImagesBaseTest {

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		actual = newImage(6, 8, BLUE);
	}

	@Test
	void should_Fail_If_Actual_Is_Null() {
		assertThrows(IllegalArgumentException.class, () -> images.assertHasSize(null, new Dimension()));
	}

	@Test
	void should_Throw_Error_If_Size_Is_Null() {
		assertThrows(IllegalArgumentException.class, () -> images.assertHasSize(actual, null));
	}

	@Test
	void should_Pass_If_Actual_Has_Size() {
		images.assertHasSize(actual, new Dimension(6, 8));
	}

	@Test
	void should_Fail_If_Actual_Has_Different_Width() {
		Dimension size = new Dimension(10, 8);
		assertThrows(AssertionFailedError.class, () -> images.assertHasSize(actual, size));
	}

	@Test
	void should_Fail_If_Actual_Has_Different_Height() {
		Dimension size = new Dimension(6, 10);
		assertThrows(AssertionFailedError.class, () -> images.assertHasSize(actual, size));
	}
}
