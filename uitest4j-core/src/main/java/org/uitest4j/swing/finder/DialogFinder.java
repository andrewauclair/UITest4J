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

import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.fixture.DialogFixture;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Finder for AWT or Swing {@code Dialog}s. This class cannot be used directly, please see {@link WindowFinder}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class DialogFinder extends WindowFinderTemplate<Dialog> {
	/**
	 * Creates a new {@link DialogFinder}.
	 *
	 * @param dialogName the name of the {@code Dialog} to look for.
	 */
	protected DialogFinder(@Nullable String dialogName) {
		super(dialogName, Dialog.class);
	}

	/**
	 * Creates a new {@link DialogFinder}.
	 *
	 * @param matcher specifies the search criteria to use when looking up a {@code Dialog}.
	 */
	protected DialogFinder(@Nonnull GenericTypeMatcher<? extends Dialog> matcher) {
		super(matcher);
	}

	/**
	 * Creates a new {@link DialogFinder}.
	 *
	 * @param dialogType the type of {@code Dialog} to look for.
	 */
	protected DialogFinder(@Nonnull Class<? extends Dialog> dialogType) {
		super(dialogType);
	}

	/**
	 * Sets the timeout for this finder. The window to search should be found within the given time period.
	 *
	 * @param timeout the number of milliseconds before stopping the search.
	 * @return this finder.
	 */
	@Override
	@Nonnull
	public DialogFinder withTimeout(@Nonnegative long timeout) {
		super.withTimeout(timeout);
		return this;
	}

	/**
	 * Sets the timeout for this finder. The window to search should be found within the given time period.
	 *
	 * @param timeout the period of time the search should be performed.
	 * @param unit    the time unit for {@code timeout}.
	 * @return this finder.
	 */
	@Override
	@Nonnull
	public DialogFinder withTimeout(@Nonnegative long timeout, @Nonnull TimeUnit unit) {
		super.withTimeout(timeout, unit);
		return this;
	}

	/**
	 * Finds a {@code Dialog} by name or type.
	 *
	 * @param robot contains the underlying finding to delegate the search to.
	 * @return a {@code DialogFixture} managing the found {@code Dialog}.
	 * @throws org.uitest4j.swing.exception.WaitTimedOutError if a {@code Dialog} could not be found.
	 */
	@Override
	@Nonnull
	public DialogFixture using(@Nonnull SwingRobot robot) {
		return new DialogFixture(robot, findComponentWith(robot));
	}

	/**
	 * Casts the given AWT or Swing {@code Component} to {@code Dialog}.
	 *
	 * @return the given {@code Component}, casted to {@code Dialog}.
	 */
	@Override
	@Nullable
	protected Dialog cast(@Nullable Component c) {
		return (Dialog) c;
	}
}