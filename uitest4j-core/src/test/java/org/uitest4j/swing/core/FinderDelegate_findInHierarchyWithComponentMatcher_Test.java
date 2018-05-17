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
import org.uitest4j.hierarchy.ComponentHierarchy;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FinderDelegate#find(ComponentHierarchy, ComponentMatcher)}.
 *
 * @author Alex Ruiz
 */
class FinderDelegate_findInHierarchyWithComponentMatcher_Test extends FinderDelegate_TestCase {
	@Test
	void should_Return_Components_Matching_ComponentMatcher() {
		ComponentMatcher matcher = c -> c instanceof JTextField && "textBox".equals(c.getName());
		Collection<Component> found = finder.find(hierarchy, matcher);
		assertThat(found).containsOnly(window.textField);
	}

	@Test
	void should_Return_Empty_Collection_If_Matching_Components_Not_Found() {
		ComponentMatcher matcher = c -> c instanceof JButton;
		Collection<Component> found = finder.find(hierarchy, matcher);
		assertThat(found).isEmpty();
	}
}
