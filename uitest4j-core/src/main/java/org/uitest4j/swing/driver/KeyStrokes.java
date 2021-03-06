/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.uitest4j.swing.exception.ActionFailedException.actionFailure;

/**
 * <p>
 * Utility methods related to {@link KeyStroke}.
 * </p>
 *
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT). Client code must call methods in this class from the EDT.
 * </p>
 *
 * @author Alex Ruiz
 */
final class KeyStrokes {
	@RunsInCurrentThread
	static @Nonnull
	KeyStroke[] findKeyStrokesForAction(@Nonnull String actionName, @Nonnull Object actionKey,
										@Nonnull InputMap inputMap) {
		List<KeyStroke> keyStrokes = new ArrayList<>();
		for (KeyStroke keyStroke : inputMap.allKeys()) {
			if (actionKey.equals(inputMap.get(keyStroke))) {
				keyStrokes.add(keyStroke);
			}
		}
		if (!keyStrokes.isEmpty()) {
			return keyStrokes.toArray(new KeyStroke[0]);
		}
		throw actionFailure(String.format("Unable to find valid input event for action with key '%s'", actionName));
	}

	private KeyStrokes() {
	}
}
