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
package org.uitest4j.javafx.hierarchy;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Window;
import org.uitest4j.core.api.javafx.NodeHierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andrew Auclair
 */
public class ExistingFXHierarchy implements NodeHierarchy {
	@Nonnull
	@Override
	public Collection<Window> roots() {
		return Window.getWindows();
	}

	@Nonnull
	@Override
	public Collection<Node> childrenOf(@Nonnull Node node) {
		if (node instanceof Parent) {
			Parent parent = (Parent) node;
			return parent.getChildrenUnmodifiable();
		}
		return new ArrayList<>();
	}

	@Nullable
	@Override
	public Parent parentOf(@Nonnull Node node) {
		return null;
	}

	@Override
	public boolean contains(@Nonnull Node node) {
		return false;
	}
}
