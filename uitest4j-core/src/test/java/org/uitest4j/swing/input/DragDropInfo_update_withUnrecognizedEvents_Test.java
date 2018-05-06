/**
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
package org.uitest4j.swing.input;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Collection;

import static java.awt.event.MouseEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.test.builder.JComboBoxes.comboBox;

/**
 * Tests for {@link DragDropInfo#update(java.awt.event.MouseEvent)}.
 * 
 * @author Alex Ruiz
 */
class DragDropInfo_update_withUnrecognizedEvents_Test extends DragDropInfo_TestCase {
  private static Collection<Object[]> eventMasks() {
    return newArrayList(new Object[][] { { MOUSE_CLICKED }, { MOUSE_DRAGGED }, { MOUSE_ENTERED }, { MOUSE_EXITED },
        { MOUSE_WHEEL } });
  }

  @ParameterizedTest
  @MethodSource("eventMasks")
  void should_Not_Update_For_Unrecognized_Events(int eventMask) {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, eventMask, when, 0, 0, 0, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isSameAs(source);
    assertThat(info.origin()).isEqualTo(origin);
  }
}
