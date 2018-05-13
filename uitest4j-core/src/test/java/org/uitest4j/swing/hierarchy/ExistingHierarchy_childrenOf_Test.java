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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import java.awt.*;
import java.util.Collection;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

/**
 * Tests for {@link ExistingHierarchy#childrenOf(Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ExistingHierarchy_childrenOf_Test extends EDTSafeTestCase {
	private Component c;
	private Collection<Component> children;
	private ChildrenFinder childrenFinder;
	private ExistingHierarchy hierarchy;

	@BeforeEach
	void setUp() {
		c = textField().createNew();
		childrenFinder = mock(ChildrenFinder.class);
		hierarchy = new ExistingHierarchy(new ParentFinder(), childrenFinder);
		children = emptyList();
	}

	@Test
	void should_Return_Children_Of_Component() {
		when(childrenFinder.childrenOf(c)).thenReturn(children);
		assertThat(hierarchy.childrenOf(c)).isSameAs(children);
	}
}
