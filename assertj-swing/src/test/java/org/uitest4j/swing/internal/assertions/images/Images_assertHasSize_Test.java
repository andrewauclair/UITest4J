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
import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.internal.assertions.ImagesBaseTest;
import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static java.awt.Color.BLUE;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.uitest4j.swing.assertions.error.ShouldHaveDimension.shouldHaveDimension;
import static org.uitest4j.swing.test.awt.AwtTestData.newImage;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

/**
 * Tests for <code>{@link Images#assertHasSize(AssertionInfo, BufferedImage, Dimension)}</code>.
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
    ExpectedException.assertContainsMessage(AssertionError.class, () -> images.assertHasSize(someInfo(), null, new Dimension()), actualIsNull());
  }

  @Test
  void should_Throw_Error_If_Size_Is_Null() {
    ExpectedException.assertContainsMessage(NullPointerException.class, () -> images.assertHasSize(someInfo(), actual, null), "The given size should not be null");
  }

  @Test
  void should_Pass_If_Actual_Has_Size() {
    images.assertHasSize(someInfo(), actual, new Dimension(6, 8));
  }

  @Test
  void should_Fail_If_Actual_Has_Different_Width() {
    Dimension size = new Dimension(10, 8);
    assertThrows(AssertionFailedError.class, () -> images.assertHasSize(someInfo(), actual, size));
  }

  @Test
  void should_Fail_If_Actual_Has_Different_Height() {
    Dimension size = new Dimension(6, 10);
    assertThrows(AssertionFailedError.class, () -> images.assertHasSize(someInfo(), actual, size));
  }

  private void verifyFailureThrownWhenSizesAreNotEqual(AssertionInfo info, Dimension size) {
    verify(failures).failure(info, shouldHaveDimension(actual, sizeOf(actual), size));
  }
}
