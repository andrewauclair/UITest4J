package org.uitest4j.javafx.hierarchy;

import javafx.scene.Node;
import javafx.scene.Parent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public interface NodeHierarchy {
	@Nonnull
	Collection<Parent> roots();

	@Nonnull
	Collection<Node> childrenOf(@Nonnull Node node);

	@Nullable
	Parent parentOf(@Nonnull Node node);

	boolean contains(@Nonnull Node node);
}
