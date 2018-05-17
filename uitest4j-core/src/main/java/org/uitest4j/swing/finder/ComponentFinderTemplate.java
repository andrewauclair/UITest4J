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
package org.uitest4j.swing.finder;

import org.uitest4j.swing.core.*;
import org.uitest4j.core.Robot;
import org.uitest4j.swing.fixture.AbstractComponentFixture;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.uitest4j.swing.timing.Pause.pause;

/**
 * Template for AWT or Swing {@code Component} finders.
 *
 * @param <T> the type of {@code Component} this finder can search.
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class ComponentFinderTemplate<T extends Component> {
	static final long TIMEOUT = 5000;

	private long timeout = TIMEOUT;

	private final ComponentMatcher matcher;
	private final String searchDescription;

	/**
	 * Creates a new {@link ComponentFinderTemplate}.
	 *
	 * @param componentName the name of the {@code Component} to find.
	 * @param componentType the type of the {@code Component} to find.
	 */
	protected ComponentFinderTemplate(@Nullable String componentName, @Nonnull Class<? extends T> componentType) {
		this(new NameMatcher(componentName, componentType, true));
	}

	/**
	 * Creates a new {@link ComponentFinderTemplate}.
	 *
	 * @param matcher specifies the search criteria to use when looking up a {@code Component}.
	 */
	protected ComponentFinderTemplate(@Nonnull GenericTypeMatcher<? extends T> matcher) {
		this((ComponentMatcher) matcher);
	}

	/**
	 * Creates a new {@link ComponentFinderTemplate}.
	 *
	 * @param componentType the type of the {@code Component} to find.
	 */
	protected ComponentFinderTemplate(@Nonnull Class<? extends T> componentType) {
		this(new TypeMatcher(componentType, true));
	}

	private ComponentFinderTemplate(@Nonnull ComponentMatcher matcher) {
		this.matcher = Objects.requireNonNull(matcher);
		searchDescription = "component to be found using matcher " + matcher;
	}

	/**
	 * Sets the timeout for this finder. The {@code Component} to find should be found within the given time period.
	 *
	 * @param newTimeout the period of time the search should be performed.
	 * @param unit       the time unit for {@code timeout}.
	 * @return this finder.
	 * @throws NullPointerException     if the time unit is {@code null}.
	 * @throws IllegalArgumentException if the timeout is a negative number.
	 */
	protected ComponentFinderTemplate<T> withTimeout(@Nonnegative long newTimeout, @Nonnull TimeUnit unit) {
		Objects.requireNonNull(unit);
		return withTimeout(unit.toMillis(newTimeout));
	}

	/**
	 * Sets the timeout for this finder. The {@code Component} to find should be found within the given time period.
	 *
	 * @param newTimeout the number of milliseconds before stopping the search.
	 * @return this finder.
	 * @throws IllegalArgumentException if the timeout is a negative number.
	 */
	@Nonnull
	protected ComponentFinderTemplate<T> withTimeout(@Nonnegative long newTimeout) {
		if (newTimeout < 0) {
			throw new IllegalArgumentException("Timeout cannot be a negative number");
		}
		this.timeout = newTimeout;
		return this;
	}

	/**
	 * Finds a component by name or type using the given robot.
	 *
	 * @param robot contains the underlying finding to delegate the search to.
	 * @return a fixture capable of managing the found component.
	 * @throws org.uitest4j.swing.exception.WaitTimedOutError if a component with the given name or of the given type could
	 *                                                        not be found.
	 */
	public abstract @Nonnull
	AbstractComponentFixture<?, T, ?> using(@Nonnull Robot robot);

	/**
	 * Finds the component using either by name or type.
	 *
	 * @param robot contains the underlying finding to delegate the search to.
	 * @return the found component.
	 * @throws org.uitest4j.swing.exception.WaitTimedOutError if a component with the given name or of the given type could
	 *                                                        not be found.
	 */
	protected final @Nonnull
	T findComponentWith(@Nonnull Robot robot) {
		ComponentFoundCondition condition = new ComponentFoundCondition(searchDescription, robot.finder(), matcher);
		pause(condition, timeout);
		return Objects.requireNonNull(cast(condition.found()));
	}

	/**
	 * Casts the given {@code Component} to the type supported by this finder.
	 *
	 * @param c the given {@code Component}.
	 * @return the given {@code Component} casted to the type supported by this finder.
	 */
	protected abstract @Nullable
	T cast(@Nullable Component c);
}
