/*
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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.timing.Condition;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;
import java.util.function.Supplier;

import static org.uitest4j.swing.format.Formatting.format;
import static org.uitest4j.swing.query.ComponentEnabledQuery.isEnabled;

/**
 * Verifies that an AWT or Swing {@code Component} is enabled.
 *
 * @author Yvonne Wang
 */
class ComponentEnabledCondition extends Condition {
	private Component c;

	static @Nonnull
	ComponentEnabledCondition untilIsEnabled(@Nonnull Component c) {
		return new ComponentEnabledCondition(c);
	}

	private ComponentEnabledCondition(@Nonnull Component c) {
		super(description(c));
		this.c = c;
	}

	@Nonnull
	private static Supplier<String> description(final @Nonnull Component c) {
		return () -> format(c) + " to be enabled";
	}

	@RunsInEDT
	@Override
	public boolean test() {
		return isEnabled(Objects.requireNonNull(c));
	}

	@Override
	protected void done() {
		c = null;
	}
}