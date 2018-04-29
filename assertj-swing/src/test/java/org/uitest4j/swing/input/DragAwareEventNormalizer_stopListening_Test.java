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
package org.uitest4j.swing.input;

import org.uitest4j.swing.test.awt.ToolkitStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.uitest4j.swing.test.awt.Toolkits.newToolkitStub;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DragAwareEventNormalizer#stopListening()}.
 * 
 * @author Alex Ruiz
 */
public class DragAwareEventNormalizer_stopListening_Test extends DragAwareEventNormalizer_TestCase {
  private ToolkitStub toolkit;
    private int mask;

  @Override
  @BeforeEach
  public void setUp() {
      EventQueueStub eventQueue = new EventQueueStub();
    toolkit = newToolkitStub();
    toolkit.eventQueue(eventQueue);
    mask = 8;
  }

  @Test
  void should_Dispose_EventQueue_When_Stops_Listening() {
    final DragAwareEventQueue dragAwareEventQueue = mock(DragAwareEventQueue.class);
    eventNormalizer = new DragAwareEventNormalizer() {
      @Override
      DragAwareEventQueue createEventQueue(@Nonnull Toolkit t, long m) {
        return dragAwareEventQueue;
      }
    };
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    eventNormalizer.stopListening();
    verify(dragAwareEventQueue).pop();
    checkEventNormalizerNotInToolkit(toolkit, mask);
  }

  @Test
  void should_Gracefully_Stop_Listening_If_DragAwareQueue_Is_Null() {
    eventNormalizer = new DragAwareEventNormalizer() {
      @Override
      DragAwareEventQueue createEventQueue(@Nonnull Toolkit t, long m) {
        throw new RuntimeException("Thrown on purpose");
      }
    };
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    eventNormalizer.stopListening();
    checkEventNormalizerNotInToolkit(toolkit, mask);
  }
}
