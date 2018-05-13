/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JComboBoxDriver#selectItem(javax.swing.JComboBox, java.util.regex.Pattern)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComboBoxDriver_selectItemByPattern_Test extends JComboBoxDriver_TestCase {
	@Test
	void should_Select_Matching_Item() {
		clearSelection();
		showWindow();
		driver.selectItem(comboBox, Pattern.compile("thi.*"));
		assertThatSelectedItemIs("third");
	}

	@Test
	void should_Not_Do_Anything_If_Item_Already_Selected() {
		showWindow();
		select(2);
		driver.selectItem(comboBox, Pattern.compile("thi.*"));
		assertThatSelectedItemIs("third");
	}

	@Test
	void should_Throw_Error_If_JComboBox_Is_Disabled() {
		disableComboBox();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectItem(comboBox, Pattern.compile("first")));
	}

	@Test
	void should_Throw_Error_If_JComboBox_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectItem(comboBox, Pattern.compile("first")));
	}

	@Test
	void should_Throw_Error_If_Item_Does_Not_Exist() {
		showWindow();
		assertThrows(LocationUnavailableException.class, () -> driver.selectItem(comboBox, Pattern.compile("hundred")));
	}
}
