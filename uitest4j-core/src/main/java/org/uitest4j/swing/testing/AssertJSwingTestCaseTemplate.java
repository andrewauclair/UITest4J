/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.testing;

import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.core.BasicSwingRobot;

import javax.annotation.Nonnull;

/**
 * Template for test cases that use AssertJ-Swing.
 *
 * @author Alex Ruiz
 */
public abstract class AssertJSwingTestCaseTemplate {
	private SwingRobot robot;

	public AssertJSwingTestCaseTemplate() {
		robot = null; // Just to satisfy FindBugs
	}

	/**
	 * Creates this test's {@link SwingRobot} using a new AWT hierarchy.
	 */
	protected final void setUpRobot() {
		robot = BasicSwingRobot.robotWithNewAwtHierarchy();
	}

	/**
	 * Cleans up resources used by this test's {@link SwingRobot}.
	 */
	protected final void cleanUp() {
		robot.cleanUp();
	}

	/**
	 * @return this test's {@link SwingRobot}
	 */
	protected final @Nonnull
	SwingRobot robot() {
		return robot;
	}
}
