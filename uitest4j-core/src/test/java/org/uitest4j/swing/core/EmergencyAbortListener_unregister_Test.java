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

import static org.uitest4j.swing.test.awt.Toolkits.singletonToolkitMock;
import static org.mockito.Mockito.verify;

import java.awt.Toolkit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EmergencyAbortListener#unregister()}.
 * 
 * @author Alex Ruiz
 */
class EmergencyAbortListener_unregister_Test {
  private Toolkit toolkit;
  private EmergencyAbortListener listener;

  @BeforeEach
  void setUp() {
    toolkit = singletonToolkitMock();
    listener = new EmergencyAbortListener(toolkit);
  }

  @Test
  void should_Unregister_From_Toolkit() {
    listener.unregister();
    verify(toolkit).removeAWTEventListener(listener);
  }
}
