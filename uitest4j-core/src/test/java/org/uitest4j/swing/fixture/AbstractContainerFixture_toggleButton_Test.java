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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JToggleButton}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_toggleButton_Test extends RobotBasedTestCase {
	private ContainerFixture fixture;
	private MyWindow window;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew(getClass());
		fixture = new ContainerFixture(robot, window);
	}

	@Test
	void should_Find_Visible_JToggleButton_By_Name() {
		robot.showWindow(window);
		JToggleButtonFixture button = fixture.toggleButton("clickMeButton");
		assertThat(button.target()).isSameAs(window.toggleButton);
	}

	@Test
	void should_Fail_If_Visible_JToggleButton_Not_Found_By_Name() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.toggleButton("myButton"), "Unable to find component using matcher",
				"name='myButton', type=javax.swing.JToggleButton, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JToggleButton_By_Type() {
		robot.showWindow(window);
		JToggleButtonFixture button = fixture.toggleButton();
		assertThat(button.target()).isSameAs(window.toggleButton);
	}

	@Test
	void should_Fail_If_Visible_JToggleButton_Not_Found_By_Type() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.toggleButton(), "Unable to find component using matcher",
				"type=javax.swing.JToggleButton, requireShowing=true");
	}

	@Test
	void shouldFindJToggleButtonWithCustomMatcher() {
		robot.showWindow(window);
		JToggleButtonFixture button = fixture.toggleButton(new GenericTypeMatcher<>(JToggleButton.class) {
			@Override
			protected boolean isMatching(@Nonnull JToggleButton b) {
				return "Click Me".equals(b.getText());
			}
		});
		assertThat(button.target()).isSameAs(window.toggleButton);
	}

	@Test
	void should_Fail_If_Visible_JToggleButton_Not_Found_By_Matcher() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.toggleButton(neverMatches(JToggleButton.class)), "Unable to find component using matcher");
	}

	private static class MyWindow extends TestWindow {
		final JToggleButton toggleButton = new JToggleButton("Click Me");

		static @Nonnull
		MyWindow createNew(final @Nonnull Class<?> testClass) {
			return checkNotNull(execute(() -> new MyWindow(testClass)));
		}

		private MyWindow(@Nonnull Class<?> testClass) {
			super(testClass);
			toggleButton.setName("clickMeButton");
			addComponents(toggleButton);
		}
	}
}
