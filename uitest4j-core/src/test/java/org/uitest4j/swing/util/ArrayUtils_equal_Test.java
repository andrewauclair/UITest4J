/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ArrayUtils#equal(String[][], String[][])}.
 *
 * @author Alex Ruiz
 */
class ArrayUtils_equal_Test {
	@Test
	void should_Return_Equal_Arrays_Jf_Both_Arrays_Are_Null() {
		assertThat(ArrayUtils.equal(null, null)).isTrue();
	}

	@Test
	void should_Return_Equal_Arrays_If_Both_Arrays_Are_Empty() {
		assertThat(ArrayUtils.equal(new String[0][], new String[0][])).isTrue();
	}

	@Test
	void should_Return_Not_Equal_Arrays_If_Only_First_Array_Is_Null() {
		assertThat(ArrayUtils.equal(null, new String[0][])).isFalse();
	}

	@Test
	void should_Return_Not_Equal_Arrays_If_Only_Second_Array_Is_Null() {
		assertThat(ArrayUtils.equal(new String[0][], null)).isFalse();
	}

	@Test
	void should_Return_Not_Equal_Arrays_If_Arrays_Have_Different_Dimensions() {
		assertThat(ArrayUtils.equal(new String[0][], new String[1][0])).isFalse();
	}

	@Test
	void should_Return_Not_Equal_Arrays_If_Arrays_Have_Different_Second_Dimensions() {
		String[][] one = {{"Hello"}};
		String[][] two = {{"Hello", "Bye"}};
		assertThat(ArrayUtils.equal(one, two)).isFalse();
	}

	@Test
	void should_Return_Not_Equal_Arrays_If_Arrays_Have_Different_Data() {
		String[][] one = {{"Hello"}};
		String[][] two = {{"Bye"}};
		assertThat(ArrayUtils.equal(one, two)).isFalse();
	}

	@Test
	void should_Return_Equal_Arrays_If_Arrays_Are_Equal() {
		String[][] one = {{"Hello"}};
		String[][] two = {{"Hello"}};
		assertThat(ArrayUtils.equal(one, two)).isTrue();
	}
}
