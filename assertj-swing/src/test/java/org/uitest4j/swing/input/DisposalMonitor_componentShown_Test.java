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

import org.assertj.swing.test.swing.TestWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.util.Maps.newHashMap;

/**
 * Tests for {@link DisposalMonitor#componentShown(ComponentEvent)}.
 *
 * @author Alex Ruiz
 */
class DisposalMonitor_componentShown_Test {
  private JFrame frame;
  private Map<Window, Boolean> disposedWindows;
  private DisposalMonitor monitor;

  @BeforeEach
  void setUp() {
    frame = TestWindow.createNewWindow(DisposalMonitor_componentShown_Test.class);
    disposedWindows = newHashMap();
    monitor = new DisposalMonitor(disposedWindows);
    frame.addComponentListener(monitor);
  }

  @Test
  void should_Remove_Component_When_Shown() {
    disposedWindows.put(frame, true);
    monitor.componentShown(new ComponentEvent(frame, 0));
    assertThat(disposedWindows).isEmpty();
    assertThat(frame.getComponentListeners()).isEmpty();
  }
}
