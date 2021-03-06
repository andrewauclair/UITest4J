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

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JComboBoxes.comboBox;

/**
 * Tests for {@link DragDropInfo#update(java.awt.event.MouseEvent)}.
 *
 * @author Alex Ruiz
 */
class DragDropInfo_update_Test extends DragDropInfo_TestCase {

	@Test
	void should_Update_Source_And_X_Y_Coordinates_On_Mouse_Pressed() {
		MouseEvent event = new MouseEvent(source, MOUSE_PRESSED, when, 0, origin.x, origin.y, 1, false, BUTTON1);
		info.update(event);
		assertThat(info.source()).isSameAs(source);
		assertThat(info.origin()).isEqualTo(origin);
	}

	@Test
	void should_Clear_Source_On_Mouse_Released() {
		info.source(source);
		info.origin(origin);
		JComboBox c = comboBox().createNew();
		MouseEvent event = new MouseEvent(c, MOUSE_RELEASED, when, 0, 7, 9, 1, false, BUTTON1);
		info.update(event);
		assertThat(info.source()).isNull();
	}

	@Test
	void should_Clear_Source_On_Mouse_Moved() {
		info.source(source);
		info.origin(origin);
		JComboBox c = comboBox().createNew();
		MouseEvent event = new MouseEvent(c, MOUSE_MOVED, when, 0, 7, 9, 1, false, BUTTON1);
		info.update(event);
		assertThat(info.source()).isNull();
	}
}
