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
package org.uitest4j.javafx;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

import javax.annotation.Nonnull;
import java.util.Objects;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;

/**
 * @author Andrew Auclair
 */
public class JavaFX {
	@Nonnull
	public static Point2D localToScreen(@Nonnull Node node, double x, double y) {
		return node.localToScreen(new Point2D(x, y));
	}

	@Nonnull
	public static Point2D visibleCenterOf(@Nonnull final Node node) {
		return Objects.requireNonNull(executeFX(() -> centerOf(node)));
	}

	@Nonnull
	public static Point2D centerOf(@Nonnull Node node) {
		Bounds bounds = node.getBoundsInLocal();
		return new Point2D(bounds.getMinX() + (bounds.getWidth() / 2.0), bounds.getMinY() + (bounds.getHeight() / 2.0));
	}
}
