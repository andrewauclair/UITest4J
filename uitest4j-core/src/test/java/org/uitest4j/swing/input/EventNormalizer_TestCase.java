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

import org.uitest4j.swing.listener.WeakEventListener;
import org.uitest4j.swing.test.awt.ToolkitStub;

import java.awt.event.AWTEventListener;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.awt.TestAWTEventListeners.newAWTEventListenerMock;

/**
 * Test case for {@link EventNormalizer} and subclasses.
 *
 * @author Alex Ruiz
 */
public abstract class EventNormalizer_TestCase {
	final void checkEventNormalizerInToolkit(ToolkitStub toolkit, EventNormalizer eventNormalizer, int mask) {
		List<WeakEventListener> listeners = toolkit.eventListenersUnderEventMask(mask, WeakEventListener.class);
		assertThat(listeners).isNotNull().hasSize(1);
		WeakEventListener weakEventListener = listeners.get(0);
		assertThat(weakEventListener.underlyingListener()).isSameAs(eventNormalizer);
	}

	final void checkEventNormalizerNotInToolkit(ToolkitStub toolkit, int mask) {
		List<WeakEventListener> listeners = toolkit.eventListenersUnderEventMask(mask, WeakEventListener.class);
		assertThat(listeners).isNullOrEmpty();
	}

	final AWTEventListener delegateEventListenerMock() {
		return newAWTEventListenerMock();
	}
}
