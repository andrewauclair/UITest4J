/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.ExpectedException;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BasicComponentFinder#findByName(java.awt.Container, String, Class, boolean)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Price
 */
class BasicComponentFinder_findByNameAndTypeInRoot_Test extends BasicComponentFinder_TestCase {
	private MyWindow windowTwo;

	@Test
	void should_Find_Component() {
		windowTwo = MyWindow.createNew(getClass());
		JButton button = finder.findByName(windowTwo, "button", JButton.class);
		assertThat(button).isSameAs(windowTwo.button);
	}

	@Test
	void should_Throw_Error_If_Component_Not_Found() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> finder.findByName(window, "list", JLabel.class), "name='list'", "type=javax.swing.JLabel");
	}

	@Test
	void should_Throw_Error_If_Component_Found_By_Name_And_Container_But_Not_By_Type() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> finder.findByName(window, "button", JLabel.class), "name='button'", "type=javax.swing.JLabel");
	}

	@Override
	void beforeReleasingScreenLock() {
		if (windowTwo != null) {
			windowTwo.destroy();
		}
	}
}
