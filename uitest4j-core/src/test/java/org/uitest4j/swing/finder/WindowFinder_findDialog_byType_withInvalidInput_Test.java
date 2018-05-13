/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.finder;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.swing.WindowLauncher.DialogToLaunch;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link WindowFinder#findDialog(Class)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class WindowFinder_findDialog_byType_withInvalidInput_Test {
	@Test
	void should_Throw_Error_If_Type_Is_Null() {
		Class<DialogToLaunch> type = null;
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> WindowFinder.findDialog(type));
	}

	@Test
	void should_Throw_Error_If_Timeout_Is_Negative() {
		assertThrows(IllegalArgumentException.class, () -> WindowFinder.findDialog(JDialog.class).withTimeout(-20));
	}

	@Test
	void should_Throw_Error_If_Time_Unit_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> WindowFinder.findDialog(JDialog.class).withTimeout(10, null));
	}
}
