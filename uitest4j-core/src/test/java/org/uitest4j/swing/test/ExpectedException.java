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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
		assertFalse(error.isExpectedDefined());
		assertFalse(error.isActualDefined());
	}

	public static void assertOpenTest4jError(Executable executable, String message, Object expected, Object actual) {
		AssertionFailedError error = assertThrows(AssertionFailedError.class, executable);
		assertEquals(message, error.getMessage());

		Objects.requireNonNull(error.getExpected(), "Expected value should not be null");
		Objects.requireNonNull(error.getActual(), "Actual value should not be null");

		assertEquals(String.valueOf(expected), error.getExpected().getStringRepresentation(), "Should have expected value '" + String.valueOf(expected) + "'");
		assertEquals(String.valueOf(actual), error.getActual().getStringRepresentation(), "Should have actual value '" + String.valueOf(actual) + "'");
	}

	public static void assertOpenTest4jError(Executable executable, String message, Object[] expected, Object[] actual) {
		AssertionFailedError error = assertThrows(AssertionFailedError.class, executable);
		assertEquals(message, error.getMessage());

		Objects.requireNonNull(error.getExpected(), "Expected value should not be null");
		Objects.requireNonNull(error.getActual(), "Actual value should not be null");

//		if (error.getExpected().getValue() != null && error.getActual().getValue() != null) {
//			assertEquals(expected, error.getExpected().getValue(), "Should have expected value '" + Arrays.toString(expected) + "'");
//			assertEquals(actual, error.getExpected().getValue(), "Should have actual value '" + Arrays.toString(actual) + "'");
//		}
//		else {
		assertEquals(Arrays.toString(expected), error.getExpected().getStringRepresentation(), "Should have expected value '" + Arrays.toString(expected) + "'");
		assertEquals(Arrays.toString(actual), error.getActual().getStringRepresentation(), "Should have actual value '" + Arrays.toString(actual) + "'");
//		}
	}

	public static void assertOpenTest4jError(Executable executable, String message, int[] expected, int[] actual) {
		AssertionFailedError error = assertThrows(AssertionFailedError.class, executable);
		assertEquals(message, error.getMessage());
		assertArrayEquals(expected, (int[]) error.getExpected().getValue());
		assertArrayEquals(actual, (int[]) error.getActual().getValue());
	}

	public static void assertAssertionError(Executable executable, String message) {
		assertContainsMessage(AssertionError.class, executable, message);
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
