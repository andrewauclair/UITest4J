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
package org.uitest4j.swing.finder;

import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JFileChooser;

import org.uitest4j.swing.core.GenericTypeMatcher;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JFileChooserFinder#findFileChooser(org.uitest4j.swing.core.GenericTypeMatcher)}.
 * 
 * @author Alex Ruiz
 */
class JFileChooserFinder_findFileChooser_withMatcher_withInvalidInput_Test {
  @Test
  void should_Throw_Error_If_Matcher_Is_Null() {
    GenericTypeMatcher<JFileChooser> matcher = null;
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> JFileChooserFinder.findFileChooser(matcher));
  }

  @Test
  void should_Throw_Error_If_Timeout_Is_Negative() {
    assertThrows(IllegalArgumentException.class, () -> JFileChooserFinder.findFileChooser(neverMatches(JFileChooser.class)).withTimeout(-20));
  }

  @Test
  void should_Throw_Error_If_Time_Unit_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> JFileChooserFinder.findFileChooser(neverMatches(JFileChooser.class)).withTimeout(10, null));
  }
}
