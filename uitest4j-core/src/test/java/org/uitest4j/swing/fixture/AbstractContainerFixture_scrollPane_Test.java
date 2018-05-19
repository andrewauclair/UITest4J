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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JScrollPane}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_scrollPane_Test extends SwingRobotBasedTestCase {
	private ContainerFixture fixture;
	private MyWindow window;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew(getClass());
		fixture = new ContainerFixture(robot, window);
	}

	@Test
	void should_Find_Visible_JScrollPane_By_Name() {
		robot.showWindow(window);
		JScrollPaneFixture scrollPane = fixture.scrollPane("scrollMeScrollBar");
		assertThat(scrollPane.target()).isSameAs(window.scrollPane);
	}

	@Test
	void should_Fail_If_Visible_JScrollPane_Not_Found_By_Name() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.scrollPane("myScrollPane"), "Unable to find component using matcher",
				"name='myScrollPane', type=javax.swing.JScrollPane, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JScrollPane_By_Type() {
		robot.showWindow(window);
		JScrollPaneFixture scrollPane = fixture.scrollPane();
		assertThat(scrollPane.target()).isSameAs(window.scrollPane);
	}

	@Test
	void should_Fail_If_Visible_JScrollPane_Not_Found_By_Type() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.scrollPane(), "Unable to find component using matcher",
				"type=javax.swing.JScrollPane, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JScrollPane_By_Matcher() {
		robot.showWindow(window);
		JScrollPaneFixture scrollPane = fixture.scrollPane(new GenericTypeMatcher<>(JScrollPane.class) {
			@Override
			protected boolean isMatching(@Nonnull JScrollPane s) {
				return s.getViewport().getView() instanceof JList;
			}
		});
		assertThat(scrollPane.target()).isSameAs(window.scrollPane);
	}

	@Test
	void should_Fail_If_Visible_JScrollPane_Not_Found_By_Matcher() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.scrollPane(neverMatches(JScrollPane.class)), "Unable to find component using matcher");
	}

	private static class MyWindow extends TestWindow {
		final JScrollPane scrollPane = new JScrollPane(new JList(array("One", "Two")));

		static @Nonnull
		MyWindow createNew(final @Nonnull Class<?> testClass) {
			return checkNotNull(execute(() -> new MyWindow(testClass)));
		}

		private MyWindow(@Nonnull Class<?> testClass) {
			super(testClass);
			scrollPane.setName("scrollMeScrollBar");
			scrollPane.setPreferredSize(new Dimension(100, 50));
			addComponents(scrollPane);
		}
	}
}
