/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.awt.ToolkitStub;

import static org.uitest4j.swing.test.awt.Toolkits.newToolkitStub;

/**
 * Tests for {@link EventNormalizer#stopListening()}.
 *
 * @author Alex Ruiz
 */
class EventNormalizer_stopListening_Test extends EventNormalizer_TestCase {
	private EventNormalizer eventNormalizer;

	@BeforeEach
	void setUp() {
		eventNormalizer = new EventNormalizer();
	}

	@Test
	void should_Detach_From_Toolkit_When_Stop_Listening() {
		ToolkitStub toolkit = newToolkitStub();
		int mask = 8;
		eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
		eventNormalizer.stopListening();
		checkEventNormalizerNotInToolkit(toolkit, mask);
	}
}
