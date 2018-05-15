package org.uitest4j.javafx.core;

import javafx.scene.Node;
import org.uitest4j.javafx.hierarchy.NodeHierarchy;
import org.uitest4j.swing.edt.GuiActionRunner;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.execute;

public class FXFinderDelegate {
	@Nonnull
	Collection<Node> find(@Nonnull NodeHierarchy hierarchy, @Nonnull NodeMatcher matcher) {
		Set<Node> found = new LinkedHashSet<>();
		for (Node node : rootsOf(hierarchy)) {
			find(hierarchy, matcher, Objects.requireNonNull(node), found);
		}
		return found;
	}

	private void find(@Nonnull NodeHierarchy hierarchy, @Nonnull NodeMatcher matcher, @Nonnull Node root,
						  @Nonnull Set<Node> found) {
		for (Node node : childrenOfNode(root, hierarchy)) {
			find(hierarchy, matcher, Objects.requireNonNull(node), found);
		}
		if (isMatching(root, matcher)) {
			found.add(root);
		}
	}

	@Nonnull
	private static Collection<Node> childrenOfNode(final @Nonnull Node node,
															 final @Nonnull NodeHierarchy hierarchy) {
		Collection<Node> children = execute(() -> hierarchy.childrenOf(node));
		return Objects.requireNonNull(children);
	}

	private static boolean isMatching(@Nonnull final Node node, @Nonnull final NodeMatcher matcher) {
		Boolean matching = GuiActionRunner.execute(() -> matcher.matches(node));
		return Objects.requireNonNull(matching);
	}

	@Nonnull
	private static Collection<? extends Node> rootsOf(final @Nonnull NodeHierarchy hierarchy) {
		return Objects.requireNonNull(execute(hierarchy::roots));
	}
}
