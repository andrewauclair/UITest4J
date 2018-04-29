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
package org.assertj.swing.internal.assertions.images;

import org.assertj.core.api.AssertionInfo;
import org.assertj.swing.internal.assertions.ImagesBaseTest;
import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.BLUE;
import static org.uitest4j.swing.assertions.data.Point.atPoint;
import static org.assertj.swing.test.awt.AwtTestData.*;

/**
 * Tests for <code>{@link Images#assertEqual(AssertionInfo, BufferedImage, BufferedImage)}</code>.
 * 
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
class Images_assertEqual_Test extends ImagesBaseTest {

  @Test
  void should_Pass_If_Images_Are_Equal() {
    images.assertEqual(someInfo(), actual, newImage(5, 5, BLUE));
  }

  @Test
  void should_Pass_If_Images_Are_Same() {
    images.assertEqual(someInfo(), actual, actual);
  }

  @Test
  void should_Pass_If_Both_Images_Are_Null() {
    images.assertEqual(someInfo(), null, null);
  }

  @Test
  void should_Fail_If_Actual_Is_Null_And_Expected_Is_Not() {
    ExpectedException.assertContainsMessage(AssertionError.class, () -> images.assertEqual(someInfo(), null, fivePixelBlueImage()), String.format("expecting images to be equal within offset:<%s>", offset.value));
  }

  @Test
  void should_Fail_If_Expected_Is_Null_And_Actual_Is_Not() {
      ExpectedException.assertContainsMessage(AssertionError.class, () -> images.assertEqual(someInfo(), actual, null), String.format("expecting images to be equal within offset:<%s>", offset.value));
  }

  @Test
  void should_Fail_If_Images_Have_Different_Size() {
    AssertionInfo info = someInfo();
    BufferedImage expected = newImage(6, 6, BLUE);

    Dimension actualSize = sizeOf(actual);
    Dimension expectedSize = sizeOf(expected);

    ExpectedException.assertContainsMessage(AssertionError.class, () -> images.assertEqual(info, actual, expected), String.format("expected size:<%sx%s> but was:<%sx%s> in:<%s>", expectedSize.width, expectedSize.height, actualSize.width,
            actualSize.height, actual));
  }

  @Test
  void should_Fail_If_Images_Have_Same_Size_But_Different_Color() {
    AssertionInfo info = someInfo();
    BufferedImage expected = fivePixelYellowImage();

    ExpectedException.assertContainsMessage(AssertionError.class, () -> images.assertEqual(info, actual, expected), String.format("expected:<%s> but was:<%s> at:<%s> within offset:<%s>", yellow(), blue(), atPoint(0, 0), offset.value));
  }
}
