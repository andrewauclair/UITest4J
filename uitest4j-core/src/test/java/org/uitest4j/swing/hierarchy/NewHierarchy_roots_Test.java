/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.hierarchy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NewHierarchy#roots()}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class NewHierarchy_roots_Test extends NewHierarchy_TestCase {
	@Test
	public void should_Not_Contain_Ignored_Windows() {
		NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
		assertThat(hierarchy.roots()).doesNotContain(window);
	}

	@Test
	public void should_Contain_Windows_That_Have_Not_Been_Ignored() {
		NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
		assertThat(hierarchy.roots()).contains(window);
	}

	@Test
	public void should_Recognize_Given_Component() {
		NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
		assertThat(hierarchy.roots()).doesNotContain(window);
		hierarchy.recognize(window);
		assertThat(hierarchy.roots()).contains(window);
	}
}
