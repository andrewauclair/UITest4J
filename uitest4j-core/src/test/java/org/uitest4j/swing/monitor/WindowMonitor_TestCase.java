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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.awt.ToolkitStub;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;

import static org.mockito.Mockito.*;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.monitor.TestContexts.newMockContext;
import static org.uitest4j.swing.monitor.TestWindows.newWindowsMock;
import static org.uitest4j.swing.test.awt.Toolkits.newToolkitStub;

/**
 * Base test case for {@link WindowMonitor}.
 *
 * @author Alex Ruiz
 */
public abstract class WindowMonitor_TestCase extends SequentialEDTSafeTestCase {
	WindowMonitor monitor;

	ToolkitStub toolkit;
	Windows windows;
	Context context;
	WindowStatus windowStatus;
	TestWindow frame;

	@Override
	protected final void onSetUp() {
		toolkit = newToolkitStub();
		windows = newWindowsMock();
		context = newMockContext();
		windowStatus = mock(WindowStatus.class);
		frame = TestWindow.createNewWindow(getClass());
		createWindowMonitor();
	}

	private void createWindowMonitor() {
		when(windowStatus.windows()).thenReturn(windows);
		monitor = execute(() -> new WindowMonitor(toolkit, context, windowStatus));
		for (Frame f : Frame.getFrames()) {
			verifyWasExamined(f);
		}
		reset(context, windows, windowStatus);
	}

	private void verifyWasExamined(Window w) {
		verify(windows).markExisting(w);
		verify(context).addContextFor(w);
		verify(windows).attachNewWindowVisibilityMonitor(w);
		for (Window owned : ownedWindowsOf(w)) {
			verifyWasExamined(owned);
		}
	}

	@RunsInEDT
	private static Window[] ownedWindowsOf(final Window w) {
		return execute(w::getOwnedWindows);
	}

	@Override
	protected final void onTearDown() {
		frame.destroy();
	}
}
