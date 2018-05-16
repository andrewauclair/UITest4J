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
package org.uitest4j.javafx.driver;

import javafx.scene.control.Label;
import org.uitest4j.driver.TextDisplayDriver;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.execute;

/**
 * @author Andrew Auclair
 */
public class LabelDriver implements TextDisplayDriver<Label> {
	@Override
	public void requireText(@Nonnull Label labeled, @Nullable String expected) {
		OpenTest4JAssertions.assertEquals(expected, textOf(labeled), () -> "Expected text of '" + labeled.getUserData() +
				"' to be '" + expected + "' but was '" + textOf(labeled) + "'");
	}

	@Override
	public void requireText(@Nonnull Label labeled, @Nonnull Pattern pattern) {
		OpenTest4JAssertions.assertMatchesPattern(pattern, textOf(labeled), () -> "Expected text of '" + labeled.getUserData() +
				"' to match pattern '" + pattern.toString() + "' but was '" + textOf(labeled) + "'");
	}

	@Nullable
	@Override
	public String textOf(@Nonnull Label labeled) {
		return execute(labeled::getText);
	}
}
