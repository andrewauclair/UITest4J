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

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BasicComponentFinder#findByName(java.awt.Container, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Price
 */
class BasicComponentFinder_findByNameInRoot_Test extends BasicComponentFinder_TestCase {
	private MyWindow windowTwo;

	@Test
	void should_Find_Component() {
		windowTwo = MyWindow.createNew(getClass());
		windowTwo.button.setName("anotherButton");
		Component button = finder.findByName(windowTwo, "anotherButton");
		assertThat(button).isSameAs(windowTwo.button);
	}

	@Test
	void should_Throw_Errror_If_Component_Not_Found() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> finder.findByName(window, "label"), "name='label'");
	}

	@Override
	void beforeReleasingScreenLock() {
		if (windowTwo != null) {
			windowTwo.destroy();
		}
	}
}
