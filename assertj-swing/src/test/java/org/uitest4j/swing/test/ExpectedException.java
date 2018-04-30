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
package org.uitest4j.swing.test;

import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.ValueWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Allows in-test specification of expected exception types and messages.
 *
 * @author Alex Ruiz
 * @author Andrew Auclair
 */
public final class ExpectedException {
	public static void assertOpenTest4jError(Executable executable, String message) {
		AssertionFailedError error = assertThrows(AssertionFailedError.class, executable);
		assertEquals(message, error.getMessage());
	}

	public static void assertOpenTest4jError(Executable executable, String message, Object expected, Object actual) {
		AssertionFailedError error = assertThrows(AssertionFailedError.class, executable);
		assertEquals(message, error.getMessage());
		assertEquals(expected, error.getExpected().getValue());
		assertEquals(actual, error.getActual().getValue());
	}

	public static void assertAssertionError(Executable executable, String message) {
		assertContainsMessage(AssertionError.class, executable, message);
	}

	public static void assertAssertionError(Executable executable, String property, String expected, String actual) {
		assertAssertionErrorForProperty(executable, property, doubleQuote(expected), doubleQuote(actual));
	}

	public static void assertAssertionError(Executable executable, String property, int expected, int actual) {
		assertAssertionErrorForProperty(executable, property, quote(expected), quote(actual));
	}

	private static String quote(int actual) {
		return "[" + actual + "]";
	}

	private static String doubleQuote(String string) {
		return "\"" + string + "\"";
	}

	public static void assertAssertionError(Executable executable, String property, String[] expected, String[] actual) {
		assertAssertionErrorForProperty(executable, property, buildStringForMessage(expected), buildStringForMessage(actual));
	}

	private static void assertAssertionErrorForProperty(Executable executable, String property, String expected, String actual) {
		assertContainsMessage(AssertionError.class, executable, "property:'" + property + "'", "expected:<" + expected + ">", "but was:<" + actual + ">");
	}

	public static void assertAssertionError(Executable executable, String property, String content, Pattern pattern) {
		String NL = System.getProperty("line.separator");
		assertContainsMessage(AssertionError.class, executable, "property:'" + property + "'", NL + "Expecting:" + NL + " \"" + content + "\"" + NL + "to match pattern:" + NL + " \"" + pattern.pattern() + "\"");
	}

	private static String buildStringForMessage(String[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < array.length; ++i) {
			sb.append("\"").append(array[i]).append("\"");
			if (i + 1 < array.length) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static void assertIllegalStateIsNotShowingComponent(Executable executable) {
		assertContainsMessage(IllegalStateException.class, executable, "Expecting component", "to be showing on the screen");
	}

	public static void assertIllegalStateIsNotResizableComponent(Executable executable) {
		assertContainsMessage(IllegalStateException.class, executable, "Expecting component", "to be resizable by the user");
	}

	public static void assertIllegalStateIsDisabledComponent(Executable executable) {
		assertContainsMessage(IllegalStateException.class, executable, "Expecting component", "to be enabled");
	}

	public static void assertContainsMessage(Class<? extends Throwable> exceptionClass, Executable executable, String... messages) {
		Throwable exception = assertThrows(exceptionClass, executable);
		List<Executable> executables = new ArrayList<>();
		for (String message : messages) {
			executables.add(() -> assertThat(exception.getMessage()).contains(message));
		}
		assertAll(executables.stream());
	}

	public static void assertDoesNotContainMessage(Class<? extends Throwable> exceptionClass, Executable executable, String... messages) {
		Throwable exception = assertThrows(exceptionClass, executable);
		List<Executable> executables = new ArrayList<>();
		for (String message : messages) {
			executables.add(() -> assertThat(exception.getMessage()).doesNotContain(message));
		}
		assertAll(executables.stream());
	}
}
