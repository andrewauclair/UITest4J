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
import java.util.concurrent.Callable;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the size of an AWT or Swing {@code Component}. This query is executed in the event dispatch thread (EDT).
 * 
 * @see Component#getSize()
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ComponentSizeQuery {
  /**
   * Returns the size of the given AWT or Swing {@code Component}. This query is executed in the event dispatch thread
   * (EDT).
   * 
   * @param component the given {@code Component}.
   * @return the size of the given {@code Component}.
   * @see Component#getSize()
   */
  @RunsInEDT
  @Nonnull public static Dimension sizeOf(final @Nonnull Component component) {
    Dimension result = execute((Callable<Dimension>) component::getSize);
    return Objects.requireNonNull(result);
  }

  private ComponentSizeQuery() {
  }
}