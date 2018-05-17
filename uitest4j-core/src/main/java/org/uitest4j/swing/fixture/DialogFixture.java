/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.uitest4j.core.Robot;
import org.uitest4j.swing.driver.DialogDriver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * Supports functional testing of {@code Dialog}s.
 *
 * @author Alex Ruiz
 */
public class DialogFixture extends AbstractWindowFixture<DialogFixture, Dialog, DialogDriver> {
	/**
	 * Creates a new {@link DialogFixture}. This constructor creates a new {@link Robot} containing the current AWT
	 * hierarchy.
	 *
	 * @param target the {@code Dialog} to be managed by this fixture.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 * @see org.uitest4j.swing.core.BasicRobot#robotWithCurrentAwtHierarchy()
	 */
	public DialogFixture(@Nonnull Dialog target) {
		super(DialogFixture.class, target);
	}

	/**
	 * Creates a new {@link DialogFixture}.
	 *
	 * @param robot  performs simulation of user events on the given {@code Dialog}.
	 * @param target the {@code Dialog} to be managed by this fixture.
	 * @throws NullPointerException if {@code robot} is {@code null}.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public DialogFixture(@Nonnull Robot robot, @Nonnull Dialog target) {
		super(DialogFixture.class, robot, target);
	}

	/**
	 * Creates a new {@link DialogFixture}.
	 *
	 * @param robot      performs simulation of user events on a {@code Dialog}.
	 * @param dialogName the name of the {@code Dialog} to find using the given {@code Robot}.
	 * @throws NullPointerException                                  if {@code robot} is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a {@code Dialog} having a matching name could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one {@code Dialog} having a matching name is found.
	 */
	public DialogFixture(@Nonnull Robot robot, @Nullable String dialogName) {
		super(DialogFixture.class, robot, dialogName, Dialog.class);
	}

	/**
	 * Creates a new {@link DialogFixture}. This constructor creates a new {@link Robot} containing the current AWT
	 * hierarchy.
	 *
	 * @param dialogName the name of the {@code Dialog} to find.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a {@code Dialog} having a matching name could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one {@code Dialog} having a matching name is found.
	 * @see org.uitest4j.swing.core.BasicRobot#robotWithCurrentAwtHierarchy()
	 */
	public DialogFixture(@Nullable String dialogName) {
		super(DialogFixture.class, dialogName, Dialog.class);
	}

	@Override
	@Nonnull
	protected DialogDriver createDriver(@Nonnull Robot robot) {
		return new DialogDriver(robot);
	}

	/**
	 * Asserts that this fixture's {@code Dialog} is modal.
	 *
	 * @return this fixture.
	 * @throws AssertionError if this fixture's {@code Dialog} is not modal.
	 */
	@Nonnull
	public DialogFixture requireModal() {
		driver().requireModal(target());
		return this;
	}

	@Nonnull
	public DialogFixture requireTitle(String expected) {
		driver().requireTitle(target(), expected);
		return this;
	}
}
