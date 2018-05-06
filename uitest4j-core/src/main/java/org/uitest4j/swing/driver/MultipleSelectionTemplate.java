/**
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

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.Robot;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import static org.uitest4j.swing.util.Platform.controlOrCommandKey;

/**
 * Simulates multiple selection on a GUI component.
 *
 * @author Yvonne Wang
 * @author Christian Rösch
 */
abstract class MultipleSelectionTemplate {
	private final Robot robot;

	MultipleSelectionTemplate(@Nonnull Robot robot) {
		this.robot = robot;
	}

	abstract int elementCount();

	@RunsInEDT
	final void multiSelect() {
		multiSelect(this::selectElement, true);
	}

	@RunsInEDT
	final void multiUnselect() {
		multiSelect(this::unselectElement, false);
	}

	@RunsInEDT
	final void multiSelect(Consumer<Integer> action, boolean firstWithoutCommandKey) {
		int elementCount = elementCount();
		if (firstWithoutCommandKey) {
			action.accept(0);
			if (elementCount == 1) {
				return;
			}
		}
		int key = controlOrCommandKey();
		robot.pressKeyWhileRunning(key, () -> {
			for (int i = firstWithoutCommandKey ? 1 : 0; i < elementCount; i++) {
				action.accept(i);
			}
		});
	}

	void selectElement(int index) {
		fail();
	}

	void unselectElement(int index) {
		fail();
	}

	private void fail() {
		throw new AssertionFailedError("Unexpected method call.");
	}
}
