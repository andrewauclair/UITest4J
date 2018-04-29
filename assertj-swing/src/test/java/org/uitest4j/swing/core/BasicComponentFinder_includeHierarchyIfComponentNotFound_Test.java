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
package org.uitest4j.swing.core;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JLabel;

import org.uitest4j.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link BasicComponentFinder#includeHierarchyIfComponentNotFound(boolean)} and
 * {@link BasicComponentFinder#includeHierarchyIfComponentNotFound()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Price
 */
class BasicComponentFinder_includeHierarchyIfComponentNotFound_Test extends BasicComponentFinder_TestCase {
  @Test
  void should_Throw_Error_Without_ComponentHierarchy_As_Configured() {
    finder.includeHierarchyIfComponentNotFound(false);
    assertThat(finder.includeHierarchyIfComponentNotFound()).isFalse();
    ExpectedException.assertDoesNotContainMessage(ComponentLookupException.class, () -> finder.findByName(window, "button", JLabel.class), "Component hierarchy:");
  }

  @Test
  void should_Throw_Error_With_ComponentHierarchy_As_Configured() {
    finder.includeHierarchyIfComponentNotFound(true);
    assertThat(finder.includeHierarchyIfComponentNotFound()).isTrue();
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> finder.findByName(window, "button", JLabel.class), "Component hierarchy:");
  }
}
