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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.listener.WeakEventListener;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NewHierarchy#NewHierarchy(java.awt.Toolkit, WindowFilter, boolean)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class NewHierarchy_constructor_Test extends NewHierarchy_TestCase {
	@Test
	void should_Ignore_Existing_Components_And_AddTransientWindowListener_To_Toolkit() {
		new NewHierarchy(toolkit, filter, true);
		assertThat(filter.isIgnored(window)).isTrue();
		assertThatTransientWindowListenerWasAddedToToolkit();
	}

	@Test
	void should_Not_Ignore_Existing_Components_And_AddTransientWindowListener_To_Toolkit() {
		new NewHierarchy(toolkit, filter, false);
		assertThat(filter.isIgnored(window)).isFalse();
		assertThatTransientWindowListenerWasAddedToToolkit();
	}

	private void assertThatTransientWindowListenerWasAddedToToolkit() {
		List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
		assertThat(eventListeners).hasSize(1);
		WeakEventListener weakEventListener = eventListeners.get(0);
		assertThat(weakEventListener.underlyingListener()).isInstanceOf(TransientWindowListener.class);
	}
}
