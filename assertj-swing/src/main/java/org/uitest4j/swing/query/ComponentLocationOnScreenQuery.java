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
package org.uitest4j.swing.query;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the location of an AWT or Swing {@code Component} on screen. This query is executed in the event dispatch
 * thread (EDT).
 *
 * @see Component#getLocationOnScreen()
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ComponentLocationOnScreenQuery {
  /**
   * Returns the location of the given AWT or Swing {@code Component} on screen. This query is executed in the event
   * dispatch thread (EDT).
   *
   * @param component the given {@code Component}.
   * @return the location of the given {@code Component} on screen.
   * @see Component#getLocationOnScreen()
   */
  @RunsInEDT
  @Nonnull public static Point locationOnScreen(final @Nonnull Component component) {
    Point result = execute(() -> component.getLocationOnScreen());
    return checkNotNull(result);
  }

  private ComponentLocationOnScreenQuery() {
  }
}