package org.uitest4j.javafx.core;

import javafx.scene.Node;
import javafx.scene.Parent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasicNodeFinder implements NodeFinder {
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
}
