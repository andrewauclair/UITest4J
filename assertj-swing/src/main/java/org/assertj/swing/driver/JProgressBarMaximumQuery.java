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
package org.assertj.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Returns the maximum value of a {@code JProgressBar}. This query is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
final class JProgressBarMaximumQuery {
  @RunsInEDT
  static int maximumOf(final @Nonnull JProgressBar progressBar) {
    Integer result = execute(() -> progressBar.getMaximum());
    return checkNotNull(result);
  }

  private JProgressBarMaximumQuery() {
  }
}
