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

import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JSplitPane}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_splitPane_Test extends SwingRobotBasedTestCase {
	private ContainerFixture fixture;
	private MyWindow window;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew(getClass());
		fixture = new ContainerFixture(robot, window);
	}

	@Test
	void should_Find_Visible_JButton_By_Name() {
		robot.showWindow(window);
		JSplitPaneFixture splitPane = fixture.splitPane("slideMeSplitPane");
		assertThat(splitPane.target()).isSameAs(window.splitPane);
	}

	@Test
	void should_Fail_If_Visible_JButton_Not_Found_By_Name() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.splitPane("mySplitPane"), "Unable to find component using matcher",
				"name='mySplitPane', type=javax.swing.JSplitPane, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JButton_By_Type() {
		robot.showWindow(window);
		JSplitPaneFixture splitPane = fixture.splitPane();
		assertThat(splitPane.target()).isSameAs(window.splitPane);
	}

	@Test
	void should_Fail_If_Visible_JButton_Not_Found_By_Type() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.splitPane(), "Unable to find component using matcher",
				"type=javax.swing.JSplitPane, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JButton_By_Matcher() {
		robot.showWindow(window);
		JSplitPaneFixture splitPane = fixture.splitPane(new GenericTypeMatcher<>(JSplitPane.class) {
			@Override
			protected boolean isMatching(@Nonnull JSplitPane s) {
				return s.getLeftComponent() instanceof JList;
			}
		});
		assertThat(splitPane.target()).isSameAs(window.splitPane);
	}

	@Test
	void should_Fail_If_Visible_JButton_Not_Found_By_Matcher() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.splitPane(neverMatches(JSplitPane.class)), "Unable to find component using matcher");
	}

	private static class MyWindow extends TestWindow {
		final JSplitPane splitPane = new JSplitPane(VERTICAL_SPLIT);

		static @Nonnull
		MyWindow createNew(final @Nonnull Class<?> testClass) {
			return checkNotNull(execute(() -> new MyWindow(testClass)));
		}

		private MyWindow(@Nonnull Class<?> testClass) {
			super(testClass);
			splitPane.setName("slideMeSplitPane");
			splitPane.setLeftComponent(list("One", "Two"));
			splitPane.setRightComponent(list("Three"));
			addComponents(splitPane);
		}

		private static JList list(@Nonnull Object... elements) {
			JList list = new JList(elements);
			list.setPreferredSize(new Dimension(100, 50));
			return list;
		}
	}
}
