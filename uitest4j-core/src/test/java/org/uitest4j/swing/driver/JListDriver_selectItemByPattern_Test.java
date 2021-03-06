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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JListDriver#selectItem(javax.swing.JList, java.util.regex.Pattern)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_selectItemByPattern_Test extends JListDriver_TestCase {
	@Test
	void should_Throw_Error_If_A_Matching_Item_Was_Not_Found() {
		showWindow();
		ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> driver.selectItem(list, Pattern.compile("ten")),
				"Unable to find item matching the pattern 'ten' among the JList contents [one, two, three]");
	}

	@Test
	void should_Not_Select_Item_If_Already_Selected() {
		select(1);
		showWindow();
		driver.selectItem(list, Pattern.compile("tw.*"));
		assertThat(selectedValue()).isEqualTo("two");
	}

	@Test
	void should_Select_Item_Matching_Pattern() {
		showWindow();
		driver.selectItem(list, Pattern.compile("tw.*"));
		assertThat(selectedValue()).isEqualTo("two");
		assertThatCellReaderWasCalled();
	}

	@Test
	void should_Throw_Error_If_JList_Is_Disabled() {
		disableList();
		ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectItem(list, Pattern.compile("tw.*")));
	}

	@Test
	void should_Throw_Error_If_JList_Is_Not_Showing_On_The_Screen() {
		ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectItem(list, Pattern.compile("tw.*")));
	}
}
