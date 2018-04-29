/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.internal.assertions.images;

import static java.awt.Color.BLUE;
import static org.uitest4j.swing.assertions.error.ShouldNotBeEqualImages.shouldNotBeEqualImages;
import static org.assertj.swing.test.awt.AwtTestData.fivePixelBlueImage;
import static org.assertj.swing.test.awt.AwtTestData.fivePixelYellowImage;
import static org.assertj.swing.test.awt.AwtTestData.newImage;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.awt.image.BufferedImage;

import org.assertj.core.api.AssertionInfo;
import org.uitest4j.swing.internal.assertions.ImagesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Images#assertNotEqual(AssertionInfo, BufferedImage, BufferedImage)}</code>.
 * 
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
class Images_assertNotEqual_Test extends ImagesBaseTest {

  @Test
  void should_Pass_If_Actual_Is_Null_And_Expected_Is_Not() {
    images.assertNotEqual(someInfo(), null, fivePixelBlueImage());
  }

  @Test
  void should_Pass_If_Expected_Is_Null_And_Actual_Is_Not() {
    images.assertNotEqual(someInfo(), actual, null);
  }

  @Test
  void should_Pass_If_Images_Have_Different_Size() {
    images.assertNotEqual(someInfo(), actual, newImage(3, 3, BLUE));
  }

  @Test
  void should_Pass_If_Images_Have_Different_Color() {
    images.assertNotEqual(someInfo(), actual, fivePixelYellowImage());
  }

  @Test
  void should_Fail_If_Images_Are_Equal() {
    AssertionInfo info = someInfo();
    BufferedImage other = newImage(5, 5, BLUE);
    try {
      assertThrows(AssertionError.class, () -> images.assertNotEqual(info, actual, other));
    } finally {
      verifyFailureThrownWhenImagesAreEqual(info);
    }
  }

  @Test
  void should_Fail_If_Images_Are_Same() {
    AssertionInfo info = someInfo();
    try {
      assertThrows(AssertionError.class, () -> images.assertNotEqual(info, actual, actual));
    } finally {
      verifyFailureThrownWhenImagesAreEqual(info);
    }
  }

  private void verifyFailureThrownWhenImagesAreEqual(AssertionInfo info) {
    verify(failures).failure(info, shouldNotBeEqualImages());
  }
}
