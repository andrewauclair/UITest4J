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
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import java.awt.*;
import java.awt.event.WindowEvent;

import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AWTEvents#wasWindowClosed(AWTEvent)}.
 *
 * @author Alex Ruiz
 */
public class AWTEvents_windowClosed_Test extends SequentialEDTSafeTestCase {
	private TestWindow source;

	@Override
	protected void onSetUp() {
		source = TestWindow.createNewWindow(getClass());
	}

	@Override
	protected void onTearDown() {
		source.destroy();
	}

	@Test
	void should_Return_True_If_Window_Closed() {
		AWTEvent event = new WindowEvent(source, WINDOW_CLOSED);
		assertThat(AWTEvents.wasWindowClosed(event)).isTrue();
	}

	@Test
	void should_Return_False_If_Window_Not_Closed() {
		AWTEvent event = new WindowEvent(source, WINDOW_CLOSING);
		assertThat(AWTEvents.wasWindowClosed(event)).isFalse();
	}
}
