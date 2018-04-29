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
package org.assertj.swing.monitor;

import org.uitest4j.swing.listener.WeakEventListener;
import org.assertj.swing.test.awt.ToolkitStub;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.List;

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static java.awt.event.WindowEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.monitor.TestContexts.newMockContext;
import static org.assertj.swing.monitor.TestWindows.newWindowsMock;
import static org.assertj.swing.test.awt.Toolkits.newToolkitStub;
import static org.assertj.swing.test.builder.JTextFields.textField;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link ContextMonitor#eventDispatched(java.awt.AWTEvent)}.
 * 
 * @author Alex Ruiz
 */
class ContextMonitor_eventDispatched_Test extends EDTSafeTestCase {
  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private ContextMonitor monitor;

  private Windows windows;
  private Context context;
  private TestWindow window;

  @BeforeEach
  void setUp() {
    window = TestWindow.createNewWindow(getClass());
    windows = newWindowsMock();
    context = newMockContext();
    monitor = new ContextMonitor(context, windows);
  }

  @AfterEach
  void tearDown() {
    window.destroy();
  }

  @Test
  void shouldAttachItSelfToToolkit() {
    ToolkitStub toolkit = newToolkitStub();
    monitor.attachTo(toolkit);
    List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
    assertThat(eventListeners).hasSize(1);
    WeakEventListener weakEventListener = eventListeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isSameAs(monitor);
  }

  @Test
  void shouldNotProcessEventIfComponentIsNotWindowOrApplet() {
    monitor.eventDispatched(new ComponentEvent(textField().createNew(), 8));
    verifyZeroInteractions(windows, context);
  }

  @Test
  void shouldProcessEventWithIdEqualToWindowOpen() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_OPENED));
    verify(context).addContextFor(window);
    verify(windows).attachNewWindowVisibilityMonitor(window);
    verify(windows).markAsShowing(window);
  }

  @Test
  void shouldProcessEventWithIdEqualToWindowOpenedAndMarkWindowAsReadyIfWindowIsFileDialog() {
    Window w = new FileDialog(window);
    when(context.storedQueueFor(w)).thenReturn(w.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(w, WINDOW_OPENED));
    verify(context).addContextFor(w);
    verify(windows).attachNewWindowVisibilityMonitor(w);
    verify(windows).markAsShowing(w);
    verify(windows).markAsReady(w);
  }

  @Test
  void shouldProcessEventWithIdEqualToWindowClosedAndWithRootWindow() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_CLOSED));
    verify(context).removeContextFor(window);
    verify(windows).markAsClosed(window);
  }

  @Test
  void shouldNotProcessEventWithIdWindowClosing() {
    when(context.storedQueueFor(window)).thenReturn(window.getToolkit().getSystemEventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_CLOSING));
    verifyZeroInteractions(windows);
  }

  @Test
  void shouldAddToContextIfComponentEventQueueNotEqualToSystemEventQueue() {
    when(context.storedQueueFor(window)).thenReturn(new EventQueue());
    monitor.eventDispatched(new ComponentEvent(window, WINDOW_CLOSING));
    verify(context).addContextFor(window);
  }
}
