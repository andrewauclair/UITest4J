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

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EventQueueMapping#addQueueFor(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class EventQueueMapping_addQueueFor_Test extends EventQueueMapping_TestCase {
	@Test
	public void should_Add_EventQueue() {
		mapping.addQueueFor(component);
		EventQueue storedEventQueue = queueMap.get(component).get();
		assertThat(storedEventQueue).isSameAs(eventQueue);
	}
}
