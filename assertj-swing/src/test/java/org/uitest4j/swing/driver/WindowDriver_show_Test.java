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
package org.uitest4j.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;
import static org.uitest4j.swing.query.ComponentVisibleQuery.isVisible;

import java.awt.Dimension;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WindowDriver#show(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 */
public class WindowDriver_show_Test extends WindowDriver_TestCase {
  @Test
  public void should_Show_Window() {
    Dimension newSize = new Dimension(600, 300);
    driver.show(window, newSize);
    assertThat(isVisible(window)).isTrue();
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }
}
