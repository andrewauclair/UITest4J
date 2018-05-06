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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.awt.TestAWTEvents.singletonAWTEventMock;
import static org.uitest4j.swing.test.builder.JFrames.frame;

/**
 * Tests for {@link DisposedWindowMonitor#isDuplicateDispose(AWTEvent)}.
 * 
 * @author Alex Ruiz
 */
class DisposedWindowMonitor_isDuplicateDispose_Test {
  private DisposedWindowMonitor monitor;
  private Window window;

  @BeforeEach
  void setUp() {
    monitor = new DisposedWindowMonitor();
    window = frame().createNew();
  }

  @Test
  void should_Return_Is_Not_Duplicate_If_Event_Is_Not_WindowEvent() {
    assertThat(monitor.isDuplicateDispose(singletonAWTEventMock())).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  @Test
  void should_Return_Is_Not_Duplicate_If_Window_Is_Closing() {
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSING);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  @Test
  void should_Return_Is_Not_Duplicate_If_Window_Is_Not_Closing_And_It_Is_Not_Closed() {
    monitor.disposedWindows.put(window, true);
    WindowEvent e = new WindowEvent(window, WINDOW_OPENED);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  @Test
  void should_Return_Is_Duplicate_If_Window_Is_Closed_And_It_Is_Marked_As_Disposed() {
    monitor.disposedWindows.put(window, true);
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSED);
    assertThat(monitor.isDuplicateDispose(e)).isTrue();
    assertThat(monitor.disposedWindows).hasSize(1);
    assertThat(monitor.disposedWindows).containsEntry(window, true);
  }

  @Test
  void should_Return_Is_Not_Duplicate_If_Window_Is_Closed_And_It_Is_Not_Marked_As_Disposed() {
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSED);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).hasSize(1).containsEntry(window, true);
    ComponentListener[] componentListeners = window.getComponentListeners();
    assertThat(componentListeners).hasSize(1);
    assertThat(componentListeners[0]).isInstanceOf(DisposalMonitor.class);
    DisposalMonitor disposalMonitor = (DisposalMonitor) componentListeners[0];
    assertThat(disposalMonitor.disposedWindows).isSameAs(monitor.disposedWindows);
  }
}
