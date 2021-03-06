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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.edt.FailOnThreadViolationRepaintManager;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JLabels.label;

/**
 * Tests for {@link NativeDndIdentifier#isNativeDragAndDrop(java.awt.AWTEvent)}.
 *
 * @author Alex Ruiz
 */
class NativeDnDIdentifier_isNativeDragAndDrop_Test extends EDTSafeTestCase {
	private NativeDndIdentifier dnd;

	@BeforeEach
	void setUp() {
		FailOnThreadViolationRepaintManager.install();
		dnd = new NativeDndIdentifier();
	}

	@Test
	void should_Return_True_If_Event_Is_Mouse_Event_And_Its_Class_Name_Is_SunDropTargetEvent() {
		assertThat(dnd.isNativeDragAndDrop(new SunDropTargetEvent())).isTrue();
	}

	@Test
	void should_Return_False_If_Event_Is_Not_Mouse_Event() {
		KeyEvent e = new KeyEvent(label().createNew(), 0, 0, 0, 0, 'a');
		assertThat(dnd.isNativeDragAndDrop(e)).isFalse();
	}

	@Test
	void should_Return_False_If_Event_Class_Name_Is_Not_SunDropTargetEvent() {
		MouseEvent e = new MouseEvent(label().createNew(), 0, 0, 0, 0, 0, 0, false);
		assertThat(dnd.isNativeDragAndDrop(e)).isFalse();
	}

	static class SunDropTargetEvent extends MouseEvent {
		SunDropTargetEvent() {
			super(label().createNew(), 0, 0, 0, 0, 0, 0, false);
		}
	}
}
