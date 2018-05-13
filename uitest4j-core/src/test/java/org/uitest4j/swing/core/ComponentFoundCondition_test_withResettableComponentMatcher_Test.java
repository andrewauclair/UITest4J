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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import java.awt.*;

import static org.mockito.Mockito.*;
import static org.uitest4j.swing.test.awt.TestComponents.singletonComponentMock;
import static org.uitest4j.swing.test.awt.TestContainers.singletonContainerMock;

/**
 * Tests for {@link ComponentFoundCondition#test()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ComponentFoundCondition_test_withResettableComponentMatcher_Test extends EDTSafeTestCase {
	private ComponentFinder finder;
	private ResettableComponentMatcher matcher;
	private static Container root;

	private ComponentFoundCondition condition;

	@BeforeAll
	public static void setUpOnce() {
		root = singletonContainerMock();
	}

	@BeforeEach
	void setUp() {
		finder = mock(ComponentFinder.class);
		matcher = mock(ResettableComponentMatcher.class);
		condition = new ComponentFoundCondition("", finder, matcher, root);
	}

	@Test
	void should_Reset_Matcher_When_Match_Not_Found() {
		when(finder.find(root, matcher)).thenThrow(new ComponentLookupException("Thrown on purpose"));
		condition.test();
		verify(matcher).reset(false);
	}

	@Test
	void should_Reset_Matcher_When_Match_Found() {
		when(finder.find(root, matcher)).thenReturn(singletonComponentMock());
		condition.test();
		verify(matcher).reset(true);
	}
}
