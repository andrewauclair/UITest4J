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

import org.assertj.swing.exception.EdtViolationException;
import org.assertj.swing.test.data.BooleanProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link EdtSafeCondition#test()}.
 * 
 * @author Alex Ruiz
 */
class EdtSafeCondition_test_Test {

  private static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  @ParameterizedTest
  @MethodSource("booleans")
  void should_Test_Condition_In_EDT(boolean conditionSatisfied) {
    EdtSafeCondition condition = new EdtSafeCondition("Hello World!") {
      @Override
      protected boolean testInEDT() {
        if (!isEventDispatchThread()) {
          throw new EdtViolationException("Method 'testInEDT' should be executed in the EDT");
        }
        return conditionSatisfied;
      }
    };

    assertThat(condition.test()).isEqualTo(conditionSatisfied);
  }
}
