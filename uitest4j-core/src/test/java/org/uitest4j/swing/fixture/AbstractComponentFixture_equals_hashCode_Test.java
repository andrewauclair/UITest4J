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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.uitest4j.core.Robot;
import org.uitest4j.swing.driver.ComponentDriver;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.mock;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link AbstractComponentFixture#equals(Object)} and {@link AbstractComponentFixture#hashCode()}.
 *
 * @author Christian Rösch
 */
public class AbstractComponentFixture_equals_hashCode_Test extends RobotBasedTestCase {
	@Test
	public void should_Work() {
		ConcreteComponentFixture one = new ConcreteComponentFixture(robot, execute((Callable<JLabel>) JLabel::new));
		ConcreteComponentFixture two = new ConcreteComponentFixture(robot, execute((Callable<JLabel>) JLabel::new));
		EqualsVerifier<AbstractComponentFixture> verifier = EqualsVerifier.forClass(AbstractComponentFixture.class);
		verifier.withPrefabValues(ConcreteComponentFixture.class, one, two);
		verifier.withPrefabValues(Component.class, execute((Callable<JLabel>) JLabel::new), execute((Callable<JButton>) JButton::new));
		verifier.withPrefabValues(Container.class, execute(Container::new), execute(Container::new));
		List<Component> listOne = newArrayList();
		List<Component> listTwo = newArrayList();
		listOne.add(execute((Callable<JCheckBox>) JCheckBox::new));
		listTwo.add(execute((Callable<JPanel>) JPanel::new));
		verifier.withPrefabValues(List.class, listOne, listTwo);
		verifier.usingGetClass().withIgnoredFields("robot", "myself", "driver").verify();
	}

	private static class ConcreteComponentFixture extends
			AbstractComponentFixture<ConcreteComponentFixture, Component, ComponentDriver> {
		public ConcreteComponentFixture(Robot robot, Component component) {
			super(ConcreteComponentFixture.class, robot, component);
		}

		@Override
		@Nonnull
		protected ComponentDriver createDriver(@Nonnull Robot robot) {
			return mock(ComponentDriver.class);
		}
	}
}
