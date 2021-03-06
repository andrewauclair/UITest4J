/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.task;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Makes an AWT or Swing {@code Component} visible or invisible. This task is executed in the event dispatch thread
 * (EDT).
 *
 * @author Alex Ruiz
 */
public final class ComponentSetVisibleTask {
	@RunsInEDT
	public static void show(@Nonnull Component c) {
		setVisible(c, true);
	}

	@RunsInEDT
	public static void hide(@Nonnull Component c) {
		setVisible(c, false);
	}

	@RunsInEDT
	public static void setVisible(final @Nonnull Component c, final boolean visible) {
		execute(() -> c.setVisible(visible));
	}

	private ComponentSetVisibleTask() {
	}
}