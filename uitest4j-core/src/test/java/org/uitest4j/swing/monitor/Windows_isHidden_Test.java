/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.monitor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Windows#isHidden(java.awt.Window)}.
 *
 * @author Alex Ruiz
 */
public class Windows_isHidden_Test extends Windows_TestCase {
	@Test
	public void should_Return_True_If_Window_Is_Hidden() {
		windows.hidden.put(window, true);
		assertThat(windows.isHidden(window)).isTrue();
	}

	@Test
	public void should_Return_False_If_Window_Is_Not_Hidden() {
		windows.hidden.remove(window);
		assertThat(windows.isHidden(window)).isFalse();
	}
}
