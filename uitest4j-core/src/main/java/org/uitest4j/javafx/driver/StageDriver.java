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

import javafx.stage.Stage;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;

import static org.uitest4j.javafx.driver.StageTitleQuery.titleOf;

/**
 * @author Andrew Auclair
 */
public class StageDriver extends NodeDriver {
	public void requireTitle(@Nonnull Stage stage, String expected) {
		String actual = titleOf(stage);
		OpenTest4JAssertions.assertEquals(expected, actual,
				() -> String.format("Expected title of '%s' to be '%s' but was '%s'", stage.getUserData(), expected, actual));
	}
}
