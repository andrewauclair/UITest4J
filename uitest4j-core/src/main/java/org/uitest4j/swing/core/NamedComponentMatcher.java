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

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;

/**
 * Matches an AWT or Swing {@code Component} by name and (optionally) by type.
 *
 * @author Alex Ruiz
 */
public final class NamedComponentMatcher extends AbstractComponentMatcher {
	private final String name;
	private final Class<? extends Component> type;

	/**
	 * Creates a new {@link NamedComponentMatcher}. The AWT or Swing {@code Component} to match does not have to be showing.
	 *
	 * @param name the name of the {@code Component} we are looking for.
	 * @throws NullPointerException     if the given name is {@code null}.
	 * @throws IllegalArgumentException if the given name is empty.
	 */
	public NamedComponentMatcher(@Nullable String name) {
		this(name, false);
	}

	/**
	 * Creates a new {@link NamedComponentMatcher}.
	 *
	 * @param name           the name of the AWT or Swing {@code Component} we are looking for.
	 * @param requireShowing indicates if the {@code Component} to match should be showing or not.
	 * @throws NullPointerException     if the given name is {@code null}.
	 * @throws IllegalArgumentException if the given name is empty.
	 */
	public NamedComponentMatcher(@Nullable String name, boolean requireShowing) {
		this(name, Component.class, requireShowing);
	}

	/**
	 * Creates a new {@link NamedComponentMatcher}. The AWT or Swing {@code Component} to match does not have to be showing.
	 *
	 * @param name the name of the {@code Component} we are looking for.
	 * @param type the type of the {@code Component} we are looking for.
	 * @throws NullPointerException     if the given name is empty.
	 * @throws IllegalArgumentException if the given name is empty.
	 * @throws NullPointerException     if the given type is {@code null}.
	 */
	public NamedComponentMatcher(@Nullable String name, @Nonnull Class<? extends Component> type) {
		this(name, type, false);
	}

	/**
	 * Creates a new {@link NamedComponentMatcher}.
	 *
	 * @param name           the name of the AWT or Swing {@code Component} we are looking for.
	 * @param type           the type of the {@code Component} we are looking for.
	 * @param requireShowing indicates if the {@code Component} to match should be showing or not.
	 * @throws NullPointerException     if the given name is empty.
	 * @throws IllegalArgumentException if the given name is empty.
	 * @throws NullPointerException     if the given type is {@code null}.
	 */
	public NamedComponentMatcher(@Nullable String name, @Nonnull Class<? extends Component> type, boolean requireShowing) {
		super(requireShowing);
		this.name = Strings.checkNotNullOrEmpty(name, "name");
		this.type = Objects.requireNonNull(type);
	}

	/**
	 * <p>
	 * Indicates whether the name and visibility of the given AWT or Swing {@code Component} matches the value specified
	 * in this matcher.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @return {@code true} if the name and visibility of the given {@code Component} matches the values specified in this
	 * matcher, {@code false} otherwise.
	 */
	@Override
	@RunsInCurrentThread
	public boolean matches(@Nullable Component c) {
		if (c == null) {
			return false;
		}
		return Objects.equals(name, c.getName()) && type.isInstance(c) && requireShowingMatches(c);
	}

	@Override
	public String toString() {
		String format = "%s[name='%s', type=%s, requireShowing=%b]";
		return String.format(format, getClass().getName(), name, type.getName(), requireShowing());
	}
}
