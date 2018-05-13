/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.format.Formatting.format;

/**
 * Tests for {@link BasicComponentPrinter#printComponents(java.io.PrintStream, ComponentMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicComponentPrinter_printComponentsUsingComponentMatcher_Test extends BasicComponentPrinter_TestCase {
	@Test
	void should_Throw_Error_If_OutputStream_Is_Null() {
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> printer.printComponents(null, new NameMatcher("button1")));
	}

	@Test
	void should_Throw_Error_If_ComponentMatcher_Is_Null() {
		ComponentMatcher matcher = null;
		// jsr305 throws IllegalArgumentExceptions when @Nonnull is used
		assertThrows(IllegalArgumentException.class, () -> printer.printComponents(out, matcher));
	}

	@Test
	void should_Print_All_Matching_Components() {
		printer.printComponents(out, new NameMatcher("button1"));
		assertThat(out.printed()).containsOnly(format(windowOne.button));
	}
}
