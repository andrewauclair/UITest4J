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
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Indicates whether an AWT or Swing {@code Component} has input focus. This query is executed in the event dispatch
 * thread (EDT).
 * 
 * @author Yvonne Wang
 */
public final class ComponentHasFocusQuery {
  /**
   * Indicates whether the given AWT or Swing {@code Component} has input focus. This query is executed in the event
   * dispatch thread (EDT).
   * 
   * @param component the given {@code Component}.
   * @return {@code true} if the given {@code Component} has input focus, {@code false} otherwise.
   * @see Component#hasFocus()
   */
  @RunsInEDT
  public static boolean hasFocus(final @Nonnull Component component) {
    Boolean result = execute(component::hasFocus);
    return Objects.requireNonNull(result);
  }

  private ComponentHasFocusQuery() {
  }
}
