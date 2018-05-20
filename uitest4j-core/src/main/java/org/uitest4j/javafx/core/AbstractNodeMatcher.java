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
package org.uitest4j.javafx.core;

import javafx.scene.Node;
import org.uitest4j.core.api.javafx.ResettableNodeMatcher;
import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;

/**
 * @author Andrew Auclair
 */
public abstract class AbstractNodeMatcher implements ResettableNodeMatcher {
	private boolean requireShowing;

	public AbstractNodeMatcher() {
		this(false);
	}

	public AbstractNodeMatcher(boolean requireShowing) {
		requireShowing(requireShowing);
	}

	protected final boolean requireShowing() {
		return requireShowing;
	}

	protected final void requireShowing(boolean requireShowing) {
		this.requireShowing = requireShowing;
	}

	@RunsInCurrentThread
	protected final boolean requireShowingMatches(@Nonnull Node node) {
		return !requireShowing || node.isVisible();
	}

	@Override
	public void reset(boolean matchFound) {

	}
}
