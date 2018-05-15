package org.uitest4j.javafx.core;

import javafx.scene.Node;

import javax.annotation.Nullable;

public interface NodeMatcher {
	boolean matches(@Nullable Node node);
}
