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

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.core.UnexpectedJOptionPaneFinder.OPTION_PANE_MATCHER;
import static org.uitest4j.swing.test.builder.JOptionPanes.optionPane;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link UnexpectedJOptionPaneFinder#requireNoJOptionPaneIsShowing}.
 * 
 * @author Alex Ruiz
 */
class UnexpectedJOptionPaneFinder_requireNoJOptionPaneIsShowing_Test extends EDTSafeTestCase {
  private ComponentFinder delegate;
  private UnexpectedJOptionPaneFinder finder;

  @BeforeEach
  void setUp() {
    delegate = mock(ComponentFinder.class);
    finder = new UnexpectedJOptionPaneFinder(delegate);
  }

  @Test
  void should_Pass_If_There_Are_Not_Any_JOptionPanes_Showing() {
    List<Component> components = newArrayList();
    when(delegate.findAll(OPTION_PANE_MATCHER)).thenReturn(components);
    finder.requireNoJOptionPaneIsShowing();
  }

  @Test
  void should_Fail_If_There_Is_A_JOptionPane_Showing() {
    List<Component> found = newArrayList();
    found.add(optionPane().createNew());
    when(delegate.findAll(OPTION_PANE_MATCHER)).thenReturn(found);
    ExpectedException.assertContainsMessage(AssertionFailedError.class, () -> finder.requireNoJOptionPaneIsShowing(), "Expecting no JOptionPane to be showing");
  }
}
