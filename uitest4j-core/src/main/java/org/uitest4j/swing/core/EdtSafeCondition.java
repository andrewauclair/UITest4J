/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.timing.Condition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * {@link Condition} that is evaluated in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 */
public abstract class EdtSafeCondition extends Condition {
	/**
	 * Creates a new {@link EdtSafeCondition}.
	 *
	 * @param description describes this condition.
	 */
	public EdtSafeCondition(@Nonnull String description) {
		super(description);
	}

	/**
	 * Creates a new {@link EdtSafeCondition}.
	 *
	 * @param description describes this condition.
	 */
	public EdtSafeCondition(@Nullable Supplier<String> description) {
		super(description);
	}

	/**
	 * Checks if the condition has been satisfied.
	 *
	 * @return {@code true} if the condition has been satisfied, otherwise {@code false}.
	 */
	@Override
	public final boolean test() {
		Boolean result = execute(this::testInEDT);
		return Objects.requireNonNull(result);
	}

	/**
	 * Checks if the condition has been satisfied. This method is guaranteed to be executed in the event dispatch thread
	 * (EDT).
	 *
	 * @return {@code true} if the condition has been satisfied, otherwise {@code false}.
	 */
	@RunsInEDT
	protected abstract boolean testInEDT();
}
