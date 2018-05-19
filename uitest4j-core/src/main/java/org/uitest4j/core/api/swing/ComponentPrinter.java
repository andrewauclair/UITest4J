/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.core.api.swing;

import org.uitest4j.core.api.Printer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.PrintStream;

/**
 * Sends the {@code String} representation of AWT and Swing {@code Component}s to a {@code java.io.PrintStream}, to
 * facilitate debugging.
 *
 * @author Alex Ruiz
 */
public interface ComponentPrinter extends Printer<Component, Container> {
}
