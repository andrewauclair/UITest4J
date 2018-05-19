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
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.core.api;

import org.uitest4j.swing.core.ComponentMatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.PrintStream;

/**
 * @author Andrew Auclair
 */
public interface Printer<C, P> {
	/**
	 * Prints all the AWT and Swing {@code Component}s in the hierarchy.
	 *
	 * @param out the output stream where to print the {@code Component}s to.
	 * @throws NullPointerException if the output stream is {@code null}.
	 * @see org.uitest4j.swing.format.Formatting#format(Component)
	 */
	void printComponents(@Nonnull PrintStream out);
	
	/**
	 * Prints all the AWT and Swing {@code Component}s in the hierarchy under the given root.
	 *
	 * @param out  the output stream where to print the {@code Component}s to.
	 * @param root the root used as the starting point of the search.
	 * @throws NullPointerException if the output stream is {@code null}.
	 * @see org.uitest4j.swing.format.Formatting#format(Component)
	 */
	void printComponents(@Nonnull PrintStream out, @Nullable P root);
	
	/**
	 * Prints only the AWT and Swing {@code Component}s of the given type in the hierarchy.
	 *
	 * @param out  the output stream where to print the {@code Component}s to.
	 * @param type the type of {@code Component}s to print.
	 * @throws NullPointerException if the output stream is {@code null}.
	 * @throws NullPointerException if {@code type} is {@code null}.
	 * @see org.uitest4j.swing.format.Formatting#format(Component)
	 */
	void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends C> type);
	
	/**
	 * Prints all the AWT and Swing {@code Component}s of the given type in the hierarchy under the given root.
	 *
	 * @param out  the output stream where to print the {@code Component}s to.
	 * @param type the type of {@code Component}s to print.
	 * @param root the root used as the starting point of the search.
	 * @throws NullPointerException if the output stream is {@code null}.
	 * @throws NullPointerException if {@code type} is {@code null}.
	 * @see org.uitest4j.swing.format.Formatting#format(Component)
	 */
	void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends C> type, @Nullable P root);
	
	/**
	 * Prints only the AWT and Swing {@code Component}s that match the given search criteria in the hierarchy.
	 *
	 * @param out     the output stream where to print the {@code Component}s to.
	 * @param matcher specifies the search criteria to use filter the {@code Component}s to print.
	 * @throws NullPointerException if the output stream is {@code null}.
	 * @throws NullPointerException if {@code matcher} is {@code null}.
	 * @see org.uitest4j.swing.format.Formatting#format(Component)
	 */
	void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher);
	
	/**
	 * Prints all the AWT and Swing {@code Component}s that match the given search criteria under the given root.
	 *
	 * @param out     the output stream where to print the {@code Component}s to.
	 * @param matcher specifies the search criteria to use filter the {@code Component}s to print.
	 * @param root    the root used as the starting point of the search.
	 * @throws NullPointerException if the output stream is {@code null}.
	 * @throws NullPointerException if {@code matcher} is {@code null}.
	 * @see org.uitest4j.swing.format.Formatting#format(Component)
	 */
	void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher, @Nullable P root);
}
