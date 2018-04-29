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

import org.uitest4j.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BasicComponentFinder#findByLabel(String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicComponentFinder_findByLabel_Test extends BasicComponentFinder_TestCase {
  @Test
  void should_Find_Component() {
    Component button = finder.findByLabel("A Label");
    assertThat(button).isSameAs(window.button);
  }

  @Test
  void should_Throw_Error_If_Component_Not_Found() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> finder.findByLabel("list"), "label='list'");
  }
}
