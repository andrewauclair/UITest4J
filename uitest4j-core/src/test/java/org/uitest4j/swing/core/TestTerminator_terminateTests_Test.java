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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link TestTerminator#terminateTests()}.
 * 
 * @author Alex Ruiz
 */
class TestTerminator_terminateTests_Test {
  private ThreadsSource threadsSource;
  private FrameDisposer frameDisposer;
  private MainThreadIdentifier mainThreadIdentifier;
  private TestTerminator terminator;

  @BeforeEach
  void setUp() {
    threadsSource = mock(ThreadsSource.class);
    frameDisposer = mock(FrameDisposer.class);
    mainThreadIdentifier = mock(MainThreadIdentifier.class);
    terminator = new TestTerminator(threadsSource, frameDisposer, mainThreadIdentifier);
  }

  @Test
  void should_Terminate_GUI_Tests() {
    Thread mainThread = mock(Thread.class);
    Thread[] allThreads = { mainThread };
    when(threadsSource.allThreads()).thenReturn(allThreads);
    when(mainThreadIdentifier.mainThreadIn(allThreads)).thenReturn(mainThread);
    terminateTestsAndCheckExpectedException();
    verify(mainThread).interrupt();
    verify(frameDisposer).disposeFrames();
  }

  @Test
  void should_Not_Throw_Error_If_Main_Thread_Not_Found() {
    Thread[] allThreads = new Thread[0];
    when(threadsSource.allThreads()).thenReturn(allThreads);
    when(mainThreadIdentifier.mainThreadIn(allThreads)).thenReturn(null);
    terminateTestsAndCheckExpectedException();
    verify(frameDisposer).disposeFrames();
  }

  private void terminateTestsAndCheckExpectedException() {
    ExpectedException.assertContainsMessage(RuntimeException.class, () -> terminator.terminateTests(), "User aborted FEST-Swing tests");
  }
}
