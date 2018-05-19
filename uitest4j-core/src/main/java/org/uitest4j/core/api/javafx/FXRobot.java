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
package org.uitest4j.core.api.javafx;

import javafx.scene.Node;
import org.uitest4j.exception.ActionFailedException;

import javax.annotation.Nonnull;

/**
 * @author Andrew Auclair
 */
public interface FXRobot {
	/**
	 * Simulates a user clicking once the given JavaFX {@code Node} using the left mouse button.
	 *
	 * @param node the {@code Node} to click on.
	 * @throws ActionFailedException if the {@code Node} to click is out of the
	 *                                                            boundaries of the screen.
	 */
	void click(@Nonnull Node node);
}
