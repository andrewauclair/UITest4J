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
import org.opentest4j.AssertionFailedError;
import org.uitest4j.core.api.javafx.NodeFinder;
import org.uitest4j.core.api.javafx.NodeHierarchy;
import org.uitest4j.core.api.javafx.NodeMatcher;
import org.uitest4j.javafx.exception.NodeLookupException;
import org.uitest4j.javafx.hierarchy.ExistingFXHierarchy;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.ComponentLookupScope;
import org.uitest4j.swing.core.Settings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Andrew Auclair
 */
public class BasicNodeFinder implements NodeFinder {
	private final NodeHierarchy hierarchy;
	private final Settings settings;

	private final FXFinderDelegate finderDelegate = new FXFinderDelegate();

//	@Nonnull
//	public static NodeFinder finderWithNewWindowHierarchy() {
//		return new BasicNodeFinder(ignoreExistingNodes());
//	}

	@Nonnull
	public static BasicNodeFinder finderWithCurrentWindowHierarchy() {
		return new BasicNodeFinder(new ExistingFXHierarchy());
	}

	protected BasicNodeFinder(@Nonnull NodeHierarchy hierarchy) {
		this(hierarchy, null);
	}

	protected BasicNodeFinder(@Nonnull NodeHierarchy hierarchy, @Nullable Settings settings) {
		this.hierarchy = hierarchy;
		this.settings = settings;
		// TODO Printer

	}

	@Override
	@Nonnull
	public <T extends Node> T findByName(@Nonnull Parent root, @Nullable String name, @Nonnull Class<T> type) {
		return findByName(root, name, type, true);
	}

	@Nonnull
	@Override
	public <T extends Node> T findByName(@Nonnull Parent root, @Nullable String name, @Nonnull Class<T> type, boolean showing) {
		return null;
	}

	@Nonnull
	@Override
	public Node find(@Nullable Parent root, @Nonnull NodeMatcher matcher) {
		return null;
	}

	@Nonnull
	@Override
	public Node findByName(@Nullable String name) {
		return findByName(name, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Node findByName(@Nullable String name, boolean showing) {
		return find(new NamedNodeMatcher(name, showing));
	}

	@Nonnull
	public Node find(@Nonnull NodeMatcher matcher) {
		return find(hierarchy, matcher);
	}

	@RunsInEDT
	@Nonnull
	private Node find(@Nonnull NodeHierarchy h, @Nonnull NodeMatcher m) {
		Collection<Node> found = finderDelegate.find(h, m);
		if (found.isEmpty()) {
			throw nodeNotFound(h, m);
		}
		if (found.size() > 1) {
			throw new AssertionFailedError("Multiple components found");
//			throw multipleComponentsFound(found, m);
		}
		return Objects.requireNonNull(found.iterator().next());
	}

	@Nonnull
	private NodeLookupException nodeNotFound(@Nonnull NodeHierarchy h, @Nonnull NodeMatcher m) {
		String message = "Unable to find node using matcher " + m + ".";
//		if (includeHierarchyIfNodeNotFound()) {
//			message += lineSeparator() + lineSeparator() + "Node Hierarchy: " + lineSeparator();
//		}
		return new NodeLookupException(message);
	}

	private boolean requireShowing() {
		return requireShowingFromSettingsOr();
	}

	/**
	 * Returns the value of the flag "requireShowing" in the {@link ComponentLookupScope} this finder's {@link Settings}.
	 * If the settings object is {@code null}, this method will return the provided default value.
	 *
	 * @return the value of the flag "requireShowing" in this finder's settings, or the provided default value if this
	 * finder does not have configuration settings.
	 */
	private boolean requireShowingFromSettingsOr() {
		if (settings == null) {
			return false;
		}
		return settings.componentLookupScope().requireShowing();
	}
}
