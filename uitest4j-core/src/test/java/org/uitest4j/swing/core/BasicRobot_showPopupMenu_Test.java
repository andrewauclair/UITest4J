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
 * Tests for {@link BasicRobot#showPopupMenu(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class BasicRobot_showPopupMenu_Test extends BasicRobot_TestCase {
	@Test
	void should_Show_PopupMenu() {
		JPopupMenu popupMenu = addPopupMenuToTextField();
		JTextField textField = window().textField();
		robot().click(textField);
		JPopupMenu found = robot().showPopupMenu(textField);
		assertThat(found).isSameAs(popupMenu);
		assertThat(found.isVisible()).isTrue();
	}

	@Test
	void should_Throw_Error_If_PopupMenu_Not_Found() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> robot().showPopupMenu(window().textField()), "Unable to show popup", "on javax.swing.JTextField");
	}
}
