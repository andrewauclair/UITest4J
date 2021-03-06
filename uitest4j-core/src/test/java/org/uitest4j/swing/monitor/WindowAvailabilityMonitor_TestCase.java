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

import org.junit.jupiter.api.BeforeEach;
import org.uitest4j.swing.test.awt.ToolkitStub;

import static java.awt.AWTEvent.*;
import static org.uitest4j.swing.monitor.TestWindows.newWindowsMock;
import static org.uitest4j.swing.test.awt.Toolkits.newToolkitStub;

/**
 * Base test case for {@link WindowAvailabilityMonitor}.
 *
 * @author Alex Ruiz
 */
public abstract class WindowAvailabilityMonitor_TestCase {
	static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

	WindowAvailabilityMonitor monitor;

	ToolkitStub toolkit;
	Windows windows;

	@BeforeEach
	public final void setUp() {
		toolkit = newToolkitStub();
		windows = newWindowsMock();
		monitor = new WindowAvailabilityMonitor(windows);
		onSetUp();
	}

	void onSetUp() {
	}
}
