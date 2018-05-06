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
package org.uitest4j.swing.hierarchy;

import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestDialog;
import org.uitest4j.swing.test.swing.TestWindow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

/**
 * Base test case for {@link TransientWindowListener}.
 * 
 * @author Alex Ruiz
 */
public class TransientWindowListener_eventDispatched_TestCase extends EDTSafeTestCase {
  TransientWindowListener listener;
  WindowFilter windowFilter;
  TestDialog eventSource;
  TestWindow parent;

  @BeforeEach
  public final void setUp() {
    windowFilter = mock(WindowFilter.class);
    listener = new TransientWindowListener(windowFilter);
    parent = TestWindow.createNewWindow(getClass());
    eventSource = TestDialog.createNewDialog(parent);
    onSetUp();
  }

  void onSetUp() {
  }

  @AfterEach
  public final void tearDown() {
    try {
      eventSource.destroy();
    } finally {
      parent.destroy();
    }
  }
}
