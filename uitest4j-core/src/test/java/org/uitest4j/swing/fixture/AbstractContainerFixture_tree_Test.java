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
import javax.swing.tree.DefaultMutableTreeNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JTree}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_tree_Test extends RobotBasedTestCase {
	private ContainerFixture fixture;
	private MyWindow window;

	@Override
	protected final void onSetUp() {
		window = MyWindow.createNew(getClass());
		fixture = new ContainerFixture(robot, window);
	}

	@Test
	void should_Find_Visible_JTree_By_Name() {
		robot.showWindow(window);
		JTreeFixture tree = fixture.tree("expandMeTree");
		assertThat(tree.target()).isSameAs(window.tree);
	}

	@Test
	void should_Fail_If_Visible_JTree_Not_Found_By_Name() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.tree("myTree"), "Unable to find component using matcher",
				"name='myTree', type=javax.swing.JTree, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JTree_By_Type() {
		robot.showWindow(window);
		JTreeFixture tree = fixture.tree();
		assertThat(tree.target()).isSameAs(window.tree);
	}

	@Test
	void should_Fail_If_Visible_JTree_Not_Found_By_Type() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.tree(), "Unable to find component using matcher",
				"type=javax.swing.JTree, requireShowing=true");
	}

	@Test
	void should_Find_Visible_JTree_By_Matcher() {
		robot.showWindow(window);
		JTreeFixture tree = fixture.tree(new GenericTypeMatcher<>(JTree.class) {
			@Override
			protected boolean isMatching(@Nonnull JTree t) {
				return "expandMeTree".equals(t.getName());
			}
		});
		assertThat(tree.target()).isSameAs(window.tree);
	}

	@Test
	void should_Fail_If_Visible_JTree_Not_Found_By_Matcher() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.tree(neverMatches(JTree.class)), "Unable to find component using matcher");
	}

	private static class MyWindow extends TestWindow {
		final JTree tree = new JTree(new DefaultMutableTreeNode("root"));

		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			tree.setName("expandMeTree");
			addComponents(tree);
		}
	}
}
