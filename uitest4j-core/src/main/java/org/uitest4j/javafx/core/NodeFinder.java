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
import javafx.scene.Parent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrew Auclair
 */
public interface NodeFinder {
	@Nonnull
	<T extends Node> T findByName(@Nonnull Parent root, @Nullable String name, @Nonnull Class<T> type);

	@Nonnull
	<T extends Node> T findByName(@Nonnull Parent root, @Nullable String name, @Nonnull Class<T> type, boolean showing);

	@Nonnull
	Node find(@Nullable Parent root, @Nonnull NodeMatcher matcher);

	@Nonnull
	Node findByName(@Nullable String name);

	@Nonnull
	Node findByName(@Nullable String name, boolean showing);

	@Nonnull
	Node find(@Nonnull NodeMatcher matcher);
}
