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
package org.uitest4j.swing.monitor;

import org.uitest4j.swing.test.awt.ToolkitStub;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

import static org.uitest4j.swing.test.awt.Toolkits.newToolkitStub;
import static org.mockito.Mockito.*;

/**
 * Base test case for {@link Context}.
 * 
 * @author Alex Ruiz
 */
public abstract class Context_TestCase extends EDTSafeTestCase {
  private ToolkitStub toolkit;

  EventQueue eventQueue;
  WindowEventQueueMapping windowEventQueueMapping;
  EventQueueMapping eventQueueMapping;
  TestWindow window;
  Context context;

  @BeforeEach
  public final void setUp() {
    eventQueue = new EventQueue();
    toolkit = newToolkitStub(eventQueue);
    window = TestWindow.createNewWindow(getClass());
    windowEventQueueMapping = mock(WindowEventQueueMapping.class);
    eventQueueMapping = mock(EventQueueMapping.class);
    createContext();
  }

  private void createContext() {
    context = new Context(toolkit, windowEventQueueMapping, eventQueueMapping);
    verify(windowEventQueueMapping).addQueueFor(toolkit);
    reset(windowEventQueueMapping);
  }

  @AfterEach
  public final void tearDown() {
    window.destroy();
  }
}
