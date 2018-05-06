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

import static org.uitest4j.swing.driver.JInternalFrameAction.MAXIMIZE;

import java.beans.PropertyVetoException;

import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.exception.UnexpectedException;
import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;

/**
 * Tests for
 * {@link JInternalFrameDriver#failIfVetoed(javax.swing.JInternalFrame, JInternalFrameAction, UnexpectedException)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JInternalFrameDriver_failIfVetoed_Test extends JInternalFrameDriver_TestCase {
  @Test
  void should_Throw_Error_If_SetProperty_Is_Vetoed() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    JInternalFrameAction action = MAXIMIZE;
    ExpectedException.assertContainsMessage(ActionFailedException.class, () -> driver.failIfVetoed(internalFrame, action, new UnexpectedException(vetoed)), action.name, "was vetoed: <Test>");
  }

  @Test
  void should_Not_Throw_Error_If_SetProperty_Is_Not_Vetoed() {
    driver.failIfVetoed(internalFrame, MAXIMIZE, new UnexpectedException(new Exception()));
  }
}
