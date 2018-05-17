/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.core.Robot;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.uitest4j.swing.driver.DialogTitleQuery.titleOf;

/**
 * <p>
 * Supports functional testing of AWT or Swing {@code Dialog}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Andrew Auclair
 */
@InternalApi
public class DialogDriver extends WindowDriver {
	/**
	 * Creates a new {@link DialogDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public DialogDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Asserts that the given AWT or Swing {@code Dialog} is modal.
	 *
	 * @param dialog the given {@code Dialog}.
	 * @throws AssertionFailedError if the given {@code Dialog} is not modal.
	 */
	@RunsInEDT
	public void requireModal(@Nonnull Dialog dialog) {
		OpenTest4JAssertions.assertTrue(dialog.isModal(), () -> "Expected '" + dialog.getName() + "' to be modal");
	}

	/**
	 * Verifies that the title of the given {@code Dialog} is equal to the expected one.
	 *
	 * @param dialog    the target {@code Dialog}.
	 * @param expected the expected title.
	 * @throws AssertionError if the title of the given {@code Dialog} is not equal to the expected one.
	 */
	@RunsInEDT
	public void requireTitle(@Nonnull Dialog dialog, String expected) {
		String actual = titleOf(dialog);
		OpenTest4JAssertions.assertEquals(expected, actual,
				() -> String.format("Expected title of '%s' to be '%s' but was '%s'", dialog.getName(), expected, actual));
	}
}
