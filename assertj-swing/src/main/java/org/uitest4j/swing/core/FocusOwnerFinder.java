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
package org.uitest4j.swing.core;

import org.assertj.core.util.VisibleForTesting;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Finds the AWT or Swing {@code Component} owning the input focus.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FocusOwnerFinder {
	private static final List<FocusOwnerFinderStrategy> STRATEGIES = new ArrayList<>();

	static {
		initializeStrategies();
	}

	@VisibleForTesting
	static void initializeStrategies() {
		replaceStrategiesWith(new ReflectionBasedFocusOwnerFinder(), new HierarchyBasedFocusOwnerFinder());
	}

	@VisibleForTesting
	static void replaceStrategiesWith(@Nonnull FocusOwnerFinderStrategy... strategies) {
		STRATEGIES.clear();
		STRATEGIES.addAll(Arrays.asList(strategies));
	}

	@VisibleForTesting
	static @Nonnull
	List<FocusOwnerFinderStrategy> strategies() {
		return new ArrayList<>(STRATEGIES);
	}

	/**
	 * @return the focus owner. This method is executed in the event dispatch thread (EDT).
	 */
	@RunsInEDT
	@Nullable
	public static Component inEdtFocusOwner() {
		return execute(FocusOwnerFinder::focusOwner);
	}

	/**
	 * <p>
	 * Returns the focus owner.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @return the focus owner.
	 */
	@RunsInCurrentThread
	@Nullable
	public static Component focusOwner() {
		for (FocusOwnerFinderStrategy strategy : STRATEGIES) {
			Component focusOwner = focusOwnerFrom(Objects.requireNonNull(strategy));
			if (focusOwner != null) {
				return focusOwner;
			}
		}
		return null;
	}

	@Nullable
	private static Component focusOwnerFrom(@Nonnull FocusOwnerFinderStrategy strategy) {
		try {
			return strategy.focusOwner();
		}
		catch (Exception e) {
			return null;
		}
	}

	private FocusOwnerFinder() {
	}
}
