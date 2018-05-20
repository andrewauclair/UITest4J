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
import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author Andrew Auclair
 */
public class NamedNodeMatcher extends AbstractNodeMatcher {
	private final String name;
	private final Class<? extends Node> type;

	public NamedNodeMatcher(@Nullable String name) {
		this(name, false);
	}

	public NamedNodeMatcher(@Nullable String name, boolean requireShowing) {
		this(name, Node.class, requireShowing);
	}

	public NamedNodeMatcher(@Nullable String name, @Nonnull Class<? extends Node> type) {
		this(name, type, false);
	}

	public NamedNodeMatcher(@Nullable String name, @Nonnull Class<? extends Node> type, boolean requireShowing) {
		super(requireShowing);
		this.name = Strings.checkNotNullOrEmpty(name, "name");
		this.type = Objects.requireNonNull(type);
	}

	@Override
	public boolean matches(@Nullable Node node) {
		if (node == null) {
			return false;
		}
		return Objects.equals(name, node.getUserData()) && type.isInstance(node) && requireShowingMatches(node);
	}

	@Override
	public String toString() {
		String format = "%s[name='%s', type=%s, requireShowing=%b]";
		return String.format(format, getClass().getName(), name, type.getName(), requireShowing());
	}
}
