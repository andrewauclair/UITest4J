/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.util;

import java.awt.Toolkit;
import java.util.Objects;

import javax.annotation.Nonnull;

/**
 * Provider of {@code Toolkit}s.
 * 
 * @author Alex Ruiz
 */
public class ToolkitProvider {
  /**
   * @return the singleton instance of this class.
   */
  @Nonnull public static ToolkitProvider instance() {
    return SingletonHolder.instance;
  }

  @Nonnull public Toolkit defaultToolkit() {
	  return Objects.requireNonNull(Toolkit.getDefaultToolkit());
  }

  // Thread-safe, lazy-loading singleton.
  private static class SingletonHolder {
    static ToolkitProvider instance = new ToolkitProvider();
  }
}
