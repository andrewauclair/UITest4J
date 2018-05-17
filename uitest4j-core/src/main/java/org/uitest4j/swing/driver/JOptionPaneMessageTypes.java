/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import java.util.Map;

import static javax.swing.JOptionPane.*;
import static org.uitest4j.exception.ActionFailedException.actionFailure;
import static org.uitest4j.swing.util.Maps.newHashMap;

/**
 * Message types of a {@code JOptionPane}.
 *
 * @author Alex Ruiz
 */
final class JOptionPaneMessageTypes {
	private static final Map<Integer, String> messageMap = newHashMap();

	static {
		messageMap.put(ERROR_MESSAGE, "Error Message");
		messageMap.put(INFORMATION_MESSAGE, "Information Message");
		messageMap.put(WARNING_MESSAGE, "Warning Message");
		messageMap.put(QUESTION_MESSAGE, "Question Message");
		messageMap.put(PLAIN_MESSAGE, "Plain Message");
	}

	static @Nonnull
	String messageTypeAsText(int messageType) {
		if (messageMap.containsKey(messageType)) {
			return Strings.checkNotNullOrEmpty(messageMap.get(messageType), "messageMap.get(messageType)");
		}
		throw actionFailure("The message type <" + messageType + "> is not valid");
	}

	private JOptionPaneMessageTypes() {
	}
}
