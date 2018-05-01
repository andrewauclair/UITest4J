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

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.data.Offset;
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.assertions.error.ShouldBeEqualColors;
import org.uitest4j.swing.internal.assertions.ImagesBaseTest;
import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.BLUE;
import static org.assertj.core.data.Offset.offset;
import static org.uitest4j.swing.assertions.data.Point.atPoint;
import static org.uitest4j.swing.assertions.error.ShouldBeEqualImages.shouldBeEqualImages;
import static org.uitest4j.swing.assertions.error.ShouldHaveDimension.shouldHaveDimension;
import static org.uitest4j.swing.test.ErrorMessages.offsetIsNull;
import static org.uitest4j.swing.test.awt.AwtTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

/**
 * Tests for <code>{@link Images#assertEqual(AssertionInfo, BufferedImage, BufferedImage, Offset)}</code>.
 * 
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
public class Images_assertEqual_with_offset_Test extends ImagesBaseTest {

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
    offset = offset(5);
  }

  @Test
  void should_Throw_Error_If_Offset_Is_Null() {
    ExpectedException.assertContainsMessage(NullPointerException.class, () -> images.assertEqual(someInfo(), actual, actual, null), offsetIsNull());
  }

  @Test
  void should_Pass_If_Images_Are_Equal() {
    Color similarBlue = new Color(0, 0, 250);
    images.assertEqual(someInfo(), actual, newImage(5, 5, similarBlue), offset);
  }

  @Test
  void should_Pass_If_Images_Are_Same() {
    images.assertEqual(someInfo(), actual, actual, offset);
  }

  @Test
  void should_Pass_If_Both_Images_Are_Null() {
    images.assertEqual(someInfo(), null, null, offset);
  }

  @Test
  void should_Fail_If_Actual_Is_Null_And_Expected_Is_Not() {
    AssertionInfo info = someInfo();
    try {
      assertThrows(AssertionError.class, () -> images.assertEqual(someInfo(), null, fivePixelBlueImage(), offset));
    } finally {
      verifyFailureThrownWhenImagesAreNotEqual(info);
    }
  }

  @Test
  void should_Fail_If_Expected_Is_Null_And_Actual_Is_Not() {
    AssertionInfo info = someInfo();
    try {
      assertThrows(AssertionError.class, () -> images.assertEqual(someInfo(), actual, null, offset));
    } finally {
      verifyFailureThrownWhenImagesAreNotEqual(info);
    }
  }

  private void verifyFailureThrownWhenImagesAreNotEqual(AssertionInfo info) {
    verify(failures).failure(info, shouldBeEqualImages(offset));
  }

  @Test
  void should_Fail_If_Images_Have_Different_Size() {
    AssertionInfo info = someInfo();
    BufferedImage expected = newImage(6, 6, BLUE);
    assertThrows(AssertionFailedError.class, () -> images.assertEqual(info, actual, expected, offset));
  }

  @Test
  void should_Fail_If_Images_Have_Same_Size_But_Different_Color() {
    AssertionInfo info = someInfo();
    BufferedImage expected = fivePixelYellowImage();
    try {
      assertThrows(AssertionError.class, () -> images.assertEqual(info, actual, expected, offset));
    } finally {
      verify(failures).failure(info, new ShouldBeEqualColors(yellow(), blue(), atPoint(0, 0), (Offset<?>) offset));
    }
  }
}
