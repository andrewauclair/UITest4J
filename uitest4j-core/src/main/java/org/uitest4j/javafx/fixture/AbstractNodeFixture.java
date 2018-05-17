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
package org.uitest4j.javafx.fixture;

import javafx.scene.Node;
import org.uitest4j.fixture.MouseInputSimulationFixture;
import org.uitest4j.javafx.driver.NodeDriver;
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.core.MouseClickInfo;

import javax.annotation.Nonnull;

/**
 * @author Andrew Auclair
 * @param <S>
 * @param <N>
 * @param <D>
 */
public class AbstractNodeFixture<S, N extends Node, D extends NodeDriver>
		implements MouseInputSimulationFixture<S> {
	
	@Nonnull
	@Override
	public S click() {
		return null;
	}
	
	@Nonnull
	@Override
	public S click(@Nonnull MouseButton button) {
		return null;
	}
	
	@Nonnull
	@Override
	public S click(@Nonnull MouseClickInfo mouseClickInfo) {
		return null;
	}
	
	@Nonnull
	@Override
	public S doubleClick() {
		return null;
	}
	
	@Nonnull
	@Override
	public S rightClick() {
		return null;
	}
}
