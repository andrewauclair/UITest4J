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
package org.uitest4j.swing.assertions.image;

import org.uitest4j.swing.assertions.ImageAssert;
import org.uitest4j.swing.assertions.ImageAssertBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.image.BufferedImage;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for <code>{@link ImageAssert#usingComparator(java.util.Comparator)}</code>.
 * 
 * @author Joel Costigliola
 */
public class ImageAssert_usingComparator_Test extends ImageAssertBaseTest {
  @Mock
  private Comparator<BufferedImage> comparator;

  @BeforeEach
  void before() {
    initMocks(this);
  }

  @Override
  @Test
  public void should_Have_Internal_Effects() {
    // in that, we don't care of the comparator, the point to check is that we can't use a comparator
    assertThrows(UnsupportedOperationException.class, () -> assertions.usingComparator(comparator));
  }

  @Override
  @Test
  public void should_Return_This() {
    // Disabled since this method throws an exception
  }

  @Override
  protected ImageAssert invoke_api_method() {
    // Not used in this test
    return null;
  }

  @Override
  protected void verify_internal_effects() {
    // Not used in this test
  }
}
