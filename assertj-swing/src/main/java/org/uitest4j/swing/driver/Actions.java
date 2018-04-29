/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.sort;
import static javax.swing.Action.NAME;
import static org.uitest4j.swing.exception.ActionFailedException.actionFailure;

/**
 * Utility methods related to Swing {@code Action}s.
 *
 * @author Alex Ruiz
 */
final class Actions {
	static @Nonnull
	Object findActionKey(@Nonnull String name, @Nonnull ActionMap actionMap) {
		Action action = actionMap.get(name);
		if (action != null) {
			return name;
		}
		Object[] allKeys = actionMap.allKeys();
		if (allKeys != null) {
			for (Object key : allKeys) {
				action = actionMap.get(key);
				if (name.equals(action.getValue(NAME))) {
					return Objects.requireNonNull(key);
				}
			}
		}
		String message = String.format("The action '%s' is not available", name);
		if (allKeys != null && allKeys.length > 0) {
			message = message + ", available actions:" + formatAllActionKeys(allKeys);
		}
		throw actionFailure(message);
	}

	@Nonnull
	private static List<String> formatAllActionKeys(@Nonnull Object[] keys) {
		List<String> formattedKeys = new ArrayList<>();
		for (Object key : keys) {
			String keyAsString = keyAsString(key);
			if (keyAsString != null) {
				formattedKeys.add(keyAsString);
			}
		}
		sort(formattedKeys);
		return formattedKeys;
	}

	@Nullable
	private static String keyAsString(@Nullable Object key) {
		if (key == null) {
			return null;
		}
		if (key instanceof String) {
			return '"' + key.toString() + '"';
		}
		return String.format("%s(%s)", key.toString(), key.getClass().getName());
	}

	private Actions() {
	}
}
