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

import javafx.scene.control.Labeled;
import org.uitest4j.fixture.TextDisplayFixture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * @author Andrew Auclair
 * @param <S>
 * @param <T>
 */
public abstract class LabeledFixture<S, T extends Labeled> implements TextDisplayFixture<S> {
	@Nullable
	@Override
	public String text() {
		return null;
	}
	
	@Nonnull
	@Override
	public S requireText(@Nullable String expected) {
		return null;
	}
	
	@Nonnull
	@Override
	public S requireText(@Nonnull Pattern pattern) {
		return null;
	}
}
