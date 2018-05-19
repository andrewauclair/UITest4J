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
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.fixture.MouseInputSimulationFixture;
import org.uitest4j.javafx.driver.NodeDriver;
import org.uitest4j.swing.core.MouseButton;
import org.uitest4j.swing.core.MouseClickInfo;
import org.uitest4j.swing.fixture.AbstractComponentFixture;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author Andrew Auclair
 * @param <S>
 * @param <N>
 * @param <D>
 */
public abstract class AbstractNodeFixture<S, C extends Node, D extends NodeDriver>
		implements MouseInputSimulationFixture<S> {
	/**
	 * Performs simulation of user events on {@link #target}
	 */
	private final SwingRobot robot;
	
	private final C target;
	private final S myself;
	
	private D driver;
	
	/**
	 * Creates a new {@link AbstractComponentFixture}.
	 *
	 * @param selfType the "self type."
	 * @param robot    performs simulation of user events on a {@code Component}.
	 * @param type     the type of the {@code Component} to find using the given {@code SwingRobot}.
	 * @throws NullPointerException                                  if {@code robot} is {@code null}.
	 * @throws NullPointerException                                  if {@code type} is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching component could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching component is found.
	 */
	public AbstractNodeFixture(@Nonnull Class<S> selfType, @Nonnull SwingRobot robot, @Nonnull Class<? extends C> type) {
		this(selfType, robot, findTarget(robot, type));
	}
	
	@Nonnull
	private static <C extends Node> C findTarget(@Nonnull SwingRobot robot, @Nonnull Class<? extends C> type) {
		Objects.requireNonNull(robot);
		Objects.requireNonNull(type);
//		return null;
		return robot.finder().findByType(type, requireShowing(robot));
	}
	
	public AbstractNodeFixture(@Nonnull Class<S> selfType, @Nonnull SwingRobot robot, @Nonnull C target) {
		myself = Objects.requireNonNull(selfType).cast(this);
		this.robot = Objects.requireNonNull(robot);
		this.target = Objects.requireNonNull(target);
		replaceDriverWith(createDriver(robot));
	}
	
	/**
	 * Returns whether showing components are the only ones participating in a component lookup. The returned value is
	 * obtained from the {@link org.uitest4j.swing.core.Settings#componentLookupScope() component lookup scope} stored in
	 * this fixture's {@link SwingRobot}.
	 *
	 * @return {@code true} if only showing components can participate in a component lookup, {@code false} otherwise.
	 */
	protected boolean requireShowing() {
		return requireShowing(robot());
	}
	
	private static boolean requireShowing(@Nonnull SwingRobot robot) {
		return robot.settings().componentLookupScope().requireShowing();
	}
	
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
	
	protected abstract @Nonnull
	D createDriver(@Nonnull SwingRobot robot);
	
	protected final @Nonnull
	D driver() {
		return driver;
	}
	
	public final void replaceDriverWith(@Nonnull D driver) {
		this.driver = Objects.requireNonNull(driver);
	}
	
	/**
	 * <p>
	 * Returns the GUI component in this fixture.
	 * </p>
	 * <p>
	 * <strong>Note:</strong> Access to the GUI component returned by this method <em>must</em> be executed in the event
	 * dispatch thread (EDT). To do so, please execute a {@link org.uitest4j.swing.edt.GuiQuery GuiQuery} or
	 * {@link org.uitest4j.swing.edt.GuiTask GuiTask} (depending on what you need to do), inside a
	 * {@link org.uitest4j.swing.edt.GuiActionRunner}. To learn more about Swing threading, please read the <a
	 * href="http://java.sun.com/javase/6/docs/api/javax/swing/package-summary.html#threading" target="_blank">Swing
	 * Threading Policy</a>.
	 * </p>
	 *
	 * @return the GUI component in this fixture.
	 */
	public final @Nonnull
	C target() {
		return target;
	}
	
	/**
	 * @return the {@link SwingRobot} that simulates user events on {@link #target()}.
	 */
	public final @Nonnull
	SwingRobot robot() {
		return robot;
	}
	
	/**
	 * @return {@code this} casted to the "self type".
	 */
	protected final @Nonnull
	S myself() {
		return myself;
	}
}
