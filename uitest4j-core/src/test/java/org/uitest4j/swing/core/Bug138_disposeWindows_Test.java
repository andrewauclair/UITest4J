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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.hierarchy.ComponentHierarchy;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.core.TestComponentHierarchies.newComponentHierarchyMock;
import static org.uitest4j.swing.test.builder.JFrames.frame;
import static org.uitest4j.swing.test.task.WindowDestroyTask.hideAndDisposeInEDT;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=138">Bug 138</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class Bug138_disposeWindows_Test extends EDTSafeTestCase {
	private ComponentHierarchy hierarchy;
	private BasicRobot robot;
	private JFrame frame;

	@BeforeEach
	void setUp() {
		hierarchy = newComponentHierarchyMock();
		robot = new TestRobotFixture(hierarchy);
	}

	@AfterEach
	void tearDown() {
		if (frame != null) {
			hideAndDisposeInEDT(frame);
		}
	}

	@Test
	void should_Dispose_Windows() {
		frame = frame().withTitle("Hello").createNew();
		List<Container> roots = newArrayList((Container) frame);
		when(hierarchy.roots()).thenReturn(roots);
		robot.cleanUp();
		verify(hierarchy).dispose(frame);
	}

	@Test
	void should_Not_Dispose_Windows() {
		robot.cleanUpWithoutDisposingWindows();
		verifyZeroInteractions(hierarchy);
	}
}
