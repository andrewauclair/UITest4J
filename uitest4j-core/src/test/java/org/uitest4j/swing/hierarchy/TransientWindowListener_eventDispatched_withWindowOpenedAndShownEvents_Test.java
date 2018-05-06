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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;

import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link TransientWindowListener#eventDispatched(AWTEvent)}.
 * 
 * @author Alex Ruiz
 */
class TransientWindowListener_eventDispatched_withWindowOpenedAndShownEvents_Test extends
    TransientWindowListener_eventDispatched_TestCase {
  private static Collection<Object[]> componentsAndEvents() {
    return newArrayList(new Object[][] { { WINDOW_OPENED }, { COMPONENT_SHOWN } });
  }

  @ParameterizedTest
  @MethodSource("componentsAndEvents")
  void should_Recognize_Window_If_It_Is_Implicitly_Ignored(int eventId) {
    when(windowFilter.isImplicitlyIgnored(eventSource)).thenReturn(true);
    listener.eventDispatched(event(eventId));
    verify(windowFilter).recognize(eventSource);
  }

  @ParameterizedTest
  @MethodSource("componentsAndEvents")
  void should_Ignore_Window_If_Parent_Is_Ignored(int eventId) {
    when(windowFilter.isImplicitlyIgnored(eventSource)).thenReturn(false);
    when(windowFilter.isIgnored(parent)).thenReturn(true);
    listener.eventDispatched(event(eventId));
    verify(windowFilter).ignore(eventSource);
  }

  private AWTEvent event(int eventId) {
    return eventId == WINDOW_OPENED ? new WindowEvent(eventSource, eventId) : new ComponentEvent(eventSource, eventId);
  }
}
