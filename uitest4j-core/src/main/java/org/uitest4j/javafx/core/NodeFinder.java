package org.uitest4j.javafx.core;

import javafx.scene.Node;
import javafx.scene.Parent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface NodeFinder {
	@Nonnull
	<T extends Node> T findByName(@Nonnull Parent root, @Nullable String name, @Nonnull Class<T> type);

	@Nonnull
	<T extends Node> T findByName(@Nonnull Parent root, @Nullable String name, @Nonnull Class<T> type, boolean showing);

	@Nonnull
	Node find(@Nullable Parent root, @Nonnull NodeMatcher matcher);
}
