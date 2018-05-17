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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.hierarchy.ComponentHierarchy;
import org.uitest4j.swing.hierarchy.ExistingHierarchy;
import org.uitest4j.swing.hierarchy.SingleComponentHierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.PrintStream;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.format.Formatting.format;
import static org.uitest4j.swing.hierarchy.NewHierarchy.ignoreExistingComponents;

/**
 * Default implementation of {@link ComponentPrinter}.
 *
 * @author Alex Ruiz
 * @see ComponentPrinter
 * @see org.uitest4j.swing.format.Formatting#format(Component)
 */
public final class BasicComponentPrinter implements ComponentPrinter {
	private static final String INDENTATION = "  ";

	private static final ComponentMatcher ALWAYS_MATCHES = alwaysMatches();

	@Nonnull
	private static ComponentMatcher alwaysMatches() {
		return c -> true;
	}

	private final ComponentHierarchy hierarchy;

	/**
	 * Creates a new {@link BasicComponentPrinter} with a new AWT hierarchy. AWT and Swing {@code Component}s created
	 * before the created {@link BasicComponentPrinter} cannot be accessed by the created {@link BasicComponentPrinter}.
	 *
	 * @return the created finder.
	 */
	@Nonnull
	public static ComponentPrinter printerWithNewAwtHierarchy() {
		return new BasicComponentPrinter(ignoreExistingComponents());
	}

	/**
	 * Creates a new {@link BasicComponentPrinter} that has access to all the AWT and Swing {@code Component}s in the AWT
	 * hierarchy.
	 *
	 * @return the created printer.
	 */
	@Nonnull
	public static ComponentPrinter printerWithCurrentAwtHierarchy() {
		return new BasicComponentPrinter(new ExistingHierarchy());
	}

	/**
	 * Creates a new {@link BasicComponentPrinter}.
	 *
	 * @param hierarchy the component hierarchy to use.
	 */
	protected BasicComponentPrinter(@Nonnull ComponentHierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	/**
	 * @return the component hierarchy used by this printer.
	 */
	protected final @Nonnull
	ComponentHierarchy hierarchy() {
		return hierarchy;
	}

	@RunsInEDT
	@Override
	public void printComponents(@Nonnull PrintStream out) {
		printComponents(out, ALWAYS_MATCHES);
	}

	@RunsInEDT
	@Override
	public void printComponents(@Nonnull PrintStream out, @Nullable Container root) {
		printComponents(out, ALWAYS_MATCHES, root);
	}

	@RunsInEDT
	@Override
	public void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends Component> type) {
		printComponents(out, type, null);
	}

	@RunsInEDT
	@Override
	public void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends Component> type,
								@Nullable Container root) {
		print(hierarchy(root), new TypeMatcher(Objects.requireNonNull(type)), Objects.requireNonNull(out));
	}

	@Override
	public void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher) {
		printComponents(out, matcher, null);
	}

	@Override
	public void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher, @Nullable Container root) {
		print(hierarchy(root), Objects.requireNonNull(matcher), Objects.requireNonNull(out));
	}

	@Nonnull
	private ComponentHierarchy hierarchy(@Nullable Container root) {
		return root != null ? new SingleComponentHierarchy(root, hierarchy) : hierarchy;
	}

	@RunsInEDT
	private static void print(@Nonnull final ComponentHierarchy hierarchy, @Nonnull final ComponentMatcher matcher,
							  @Nonnull final PrintStream out) {
		execute(() -> {
			for (Component c : hierarchy.roots()) {
				print(Objects.requireNonNull(c), hierarchy, matcher, 0, out);
			}
		});
	}

	@RunsInCurrentThread
	private static void print(@Nonnull Component c, @Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher matcher,
							  int level, @Nonnull PrintStream out) {
		if (matcher.matches(c)) {
			print(c, level, out);
		}
		for (Component child : h.childrenOf(c)) {
			print(Objects.requireNonNull(child), h, matcher, level + 1, out);
		}
	}

	@RunsInCurrentThread
	private static void print(@Nonnull Component c, int level, @Nonnull PrintStream out) {
		for (int i = 0; i < level; i++) {
			out.print(INDENTATION);
		}
		out.println(format(c));
	}
}
