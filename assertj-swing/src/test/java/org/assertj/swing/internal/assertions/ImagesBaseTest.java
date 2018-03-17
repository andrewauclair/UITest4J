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
package org.assertj.swing.internal.assertions;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.data.Offset;
import org.assertj.core.internal.Failures;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.assertj.core.data.Offset.offset;
import static org.assertj.swing.test.awt.AwtTestData.fivePixelBlueImage;
import static org.mockito.Mockito.spy;

/**
 * Base class for {@link Images} unit tests
 * <p>
 * Is in <code>org.fest.assertions.internal</code> package to be able to set {@link Images} attributes appropriately.
 * 
 * @author Joel Costigliola
 * 
 */
public class ImagesBaseTest {
  protected BufferedImage actual;
  protected Offset<Integer> offset;
  protected Failures failures;
  protected Images images;

  @BeforeEach
  public void setUp() {
    failures = spy(Failures.instance());
    images = new Images();
    images.failures = failures;
    actual = fivePixelBlueImage();
    offset = offset(0);
  }

  protected Dimension sizeOf(BufferedImage actual) {
    return Images.sizeOf(actual);
  }

  private static final AssertionInfo ASSERTION_INFO = new WritableAssertionInfo();

  protected AssertionInfo someInfo() {
    return ASSERTION_INFO;
  }
}