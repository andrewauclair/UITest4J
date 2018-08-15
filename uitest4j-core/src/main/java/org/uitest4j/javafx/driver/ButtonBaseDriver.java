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

import javafx.scene.control.Button;
import org.uitest4j.driver.TextDisplayDriver;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;

/**
 * @author Andrew Auclair
 */
public class ButtonBaseDriver implements TextDisplayDriver<Button> {
	@Override
	public void requireText(@Nonnull Button button, @Nullable String expected) {
		String actual = textOf(button);
		OpenTest4JAssertions.assertEquals(expected, actual, () -> "Expected text of '" + button.getUserData() +
				"' to be '" + expected + "' but was '" + actual + "'");
	}
	
	@Override
	public void requireText(@Nonnull Button button, @Nonnull Pattern pattern) {
		String actual = textOf(button);
		OpenTest4JAssertions.assertMatchesPattern(pattern, actual,
				() -> String.format("Expected text of '%s' to match pattern '%s' but was '%s'",
						button.getUserData(), pattern, actual));
	}
	
	@Nullable
	@Override
	public String textOf(@Nonnull Button button) {
		return executeFX(button::getText);
	}
}
