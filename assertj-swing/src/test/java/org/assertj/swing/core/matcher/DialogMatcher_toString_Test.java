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
package org.assertj.swing.core.matcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DialogMatcher#toString()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogMatcher_toString_Test {
  @Test
  public void should_Implement_ToString() {
    DialogMatcher matcher = DialogMatcher.withName("dialog").andTitle("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.assertj.swing.core.matcher.DialogMatcher[name='dialog', title='Hello', requireShowing=true]");
  }
}
