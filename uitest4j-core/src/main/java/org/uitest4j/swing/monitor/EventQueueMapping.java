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

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.uitest4j.swing.util.Maps.newWeakHashMap;

/**
 * A mapping of AWT or Swing {@code Component}s, and their respective {@link EventQueue}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class EventQueueMapping {
	final Map<Component, WeakReference<EventQueue>> queueMap = newWeakHashMap();

	@RunsInCurrentThread
	void addQueueFor(@Nonnull Component c) {
		EventQueue queue = c.getToolkit().getSystemEventQueue();
		queueMap.put(c, new WeakReference<>(queue));
	}

	@RunsInCurrentThread
	@Nullable
	EventQueue queueFor(@Nonnull Component c) {
		EventQueue queue = storedQueueFor(c);
		if (queue == null) {
			return c.getToolkit().getSystemEventQueue();
		}
		return queue;
	}

	@Nullable
	EventQueue storedQueueFor(@Nonnull Component c) {
		return queueFrom(queueMap.get(c));
	}

	@Nonnull
	Collection<EventQueue> eventQueues() {
		Set<EventQueue> eventQueues = new HashSet<>();
		for (WeakReference<EventQueue> reference : queueMap.values()) {
			EventQueue queue = queueFrom(reference);
			if (queue != null) {
				eventQueues.add(queue);
			}
		}
		return eventQueues;
	}

	@Nullable
	private EventQueue queueFrom(@Nullable WeakReference<EventQueue> reference) {
		if (reference == null) {
			return null;
		}
		return reference.get();
	}
}
