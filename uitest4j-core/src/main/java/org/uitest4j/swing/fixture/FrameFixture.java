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

import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.core.BasicSwingRobot;
import org.uitest4j.swing.driver.FrameDriver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * Supports functional testing of {@code Frame}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author Christian Rösch
 */
public class FrameFixture extends AbstractWindowFixture<FrameFixture, Frame, FrameDriver> implements
		FrameLikeFixture<FrameFixture> {
	/**
	 * Creates a new {@link FrameFixture}. This constructor creates a new {@link SwingRobot} containing the current AWT
	 * hierarchy.
	 *
	 * @param target the {@code Frame} to be managed by this fixture.
	 * @throws NullPointerException if the given frame is {@code null}.
	 * @see BasicSwingRobot#robotWithCurrentAwtHierarchy()
	 */
	public FrameFixture(@Nonnull Frame target) {
		super(FrameFixture.class, target);
	}

	/**
	 * Creates a new {@link FrameFixture}.
	 *
	 * @param robot  performs user events on the given window and verifies expected output.
	 * @param target the {@code Frame} to be managed by this fixture.
	 * @throws NullPointerException if the given robot is {@code null}.
	 * @throws NullPointerException if the given frame is {@code null}.
	 */
	public FrameFixture(@Nonnull SwingRobot robot, @Nonnull Frame target) {
		super(FrameFixture.class, robot, target);
	}

	/**
	 * Creates a new {@link FrameFixture}.
	 *
	 * @param robot performs user events on the given window and verifies expected output.
	 * @param name  the name of the {@code Frame} to find using the given {@code SwingRobot}.
	 * @throws NullPointerException                                  if the given robot is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a {@code Frame} having a matching name could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one {@code Frame} having a matching name is found.
	 */
	public FrameFixture(@Nonnull SwingRobot robot, @Nullable String name) {
		super(FrameFixture.class, robot, name, Frame.class);
	}

	/**
	 * Creates a new {@link FrameFixture}. This constructor creates a new {@link SwingRobot} containing the current AWT
	 * hierarchy.
	 *
	 * @param name the name of the {@code Frame} to find.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a {@code Frame} having a matching name could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one {@code Frame} having a matching name is found.
	 */
	public FrameFixture(@Nullable String name) {
		super(FrameFixture.class, name, Frame.class);
	}

	@Override
	@Nonnull
	protected FrameDriver createDriver(@Nonnull SwingRobot robot) {
		return new FrameDriver(robot);
	}

	/**
	 * Simulates a user iconifying this fixture's {@code Frame}.
	 *
	 * @return this fixture.
	 */
	@Override
	@Nonnull
	public FrameFixture iconify() {
		driver().iconify(target());
		return this;
	}

	/**
	 * Simulates a user deiconifying this fixture's {@code Frame}.
	 *
	 * @return this fixture.
	 */
	@Override
	@Nonnull
	public FrameFixture deiconify() {
		driver().deiconify(target());
		return this;
	}

	/**
	 * Simulates a user maximizing this fixture's {@code Frame}.
	 *
	 * @return this fixture.
	 * @throws ActionFailedException if the operating system does not support maximizing frames.
	 */
	@Override
	@Nonnull
	public FrameFixture maximize() {
		driver().maximize(target());
		return this;
	}

	/**
	 * Simulates a user normalizing this fixture's {@code Frame}.
	 *
	 * @return this fixture.
	 */
	@Override
	@Nonnull
	public FrameFixture normalize() {
		driver().normalize(target());
		return this;
	}

	@Override
	@Nonnull
	public FrameFixture requireTitle(String expected) {
		driver().requireTitle(target(), expected);
		return this;
	}
}
