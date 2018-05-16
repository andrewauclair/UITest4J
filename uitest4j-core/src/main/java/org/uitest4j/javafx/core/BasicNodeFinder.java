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

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.uitest4j.javafx.hierarchy.NodeHierarchy;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.NameMatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Andrew Auclair
 */
public class BasicNodeFinder implements NodeFinder {
	private final NodeHierarchy hierarchy = null;
	private Stage stage;

	public BasicNodeFinder(Stage stage) {
		this.stage = stage;
		stage.getScene().getRoot();

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
		return findByName(name, true);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Node findByName(@Nullable String name, boolean showing) {
//		return find(new NameMatcher(name, showing));
		return null;
	}

	@Nonnull
	public Node find(@Nonnull NodeMatcher matcher) {
//		return find(hierarchy, matcher);
		return null;
	}

	@RunsInEDT
	@Nonnull
	private Component find(@Nonnull NodeHierarchy h, @Nonnull NodeMatcher m) {
//		Collection<Component> found = finderDelegate.find(h, m);
//		if (found.isEmpty()) {
//			throw componentNotFound(h, m);
//		}
//		if (found.size() > 1) {
//			throw multipleComponentsFound(found, m);
//		}
//		return Objects.requireNonNull(found.iterator().next());

		return null;
	}

//	@RunsInEDT
//	@Nonnull
//	private ComponentLookupException componentNotFound(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
//		String message = "Unable to find component using matcher " + m + ".";
//		if (includeHierarchyIfComponentNotFound()) {
//			message = message + lineSeparator() + lineSeparator() + "Component hierarchy:" + lineSeparator() +
//					formattedHierarchy(root(h));
//		}
//		throw new ComponentLookupException(message);
//	}
}
