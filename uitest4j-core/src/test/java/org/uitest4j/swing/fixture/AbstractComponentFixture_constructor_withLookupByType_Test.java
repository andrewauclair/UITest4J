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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.ComponentFinder;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.core.Settings;
import org.uitest4j.swing.driver.ComponentDriver;

import javax.annotation.Nonnull;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.core.ComponentLookupScope.SHOWING_ONLY;

/**
 * Tests for {@link AbstractComponentFixture#AbstractComponentFixture(Class, Robot, Class)}.
 *
 * @author Alex Ruiz
 */
class AbstractComponentFixture_constructor_withLookupByType_Test {
	private Robot robot;
	private Class<Frame> type;

	@BeforeEach
	void setUp() {
		robot = mock(Robot.class);
		type = Frame.class;
	}

	@Test
	void should_Throw_Error_If_SelfType_Is_Null() {
		assertThrows(IllegalArgumentException.class, () -> new ComponentFixture(null, robot, type));
	}

	@Test
	void should_Throw_Error_If_Robot_Is_Null() {
		assertThrows(IllegalArgumentException.class, () -> new ComponentFixture(ComponentFixture.class, null, type));
	}

	@Test
	void should_Throw_Error_If_Class_Is_Null() {
		assertThrows(IllegalArgumentException.class, () -> new ComponentFixture(ComponentFixture.class, robot, null));
	}

	@Test
	void should_Lookup_Component_By_Name_And_Type() {
		Frame frame = mock(type);
		Settings settings = mock(Settings.class);
		when(robot.settings()).thenReturn(settings);
		when(settings.componentLookupScope()).thenReturn(SHOWING_ONLY);
		ComponentFinder finder = mock(ComponentFinder.class);
		when(robot.finder()).thenReturn(finder);
		when(finder.findByType(type, true)).thenReturn(frame);
		ComponentFixture fixture = new ComponentFixture(ComponentFixture.class, robot, type);
		assertThat(fixture.robot()).isSameAs(robot);
		assertThat(fixture.target()).isSameAs(frame);
	}

	private static class ComponentFixture extends AbstractComponentFixture<ComponentFixture, Component, ComponentDriver> {
		ComponentFixture(@Nonnull Class<ComponentFixture> selfType, @Nonnull Robot robot,
						 @Nonnull Class<? extends Component> type) {
			super(selfType, robot, type);
		}

		@Override
		@Nonnull
		protected ComponentDriver createDriver(@Nonnull Robot robot) {
			return new ComponentDriver(robot);
		}
	}
}
