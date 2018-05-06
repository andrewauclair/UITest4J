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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static javax.swing.Action.NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link Actions#findActionKey(String, ActionMap)}.
 * 
 * @author Alex Ruiz
 */
class Actions_findActionKey_Test {
  private ActionMap map;
  private Action action;

  @BeforeEach
  void setUp() {
    map = new ActionMap();
    action = mock(Action.class);
    map.put("key", action);
  }

  @Test
  void should_Return_Given_Name_If_Equal_To_Key() {
    Object found = Actions.findActionKey("key", map);
    assertThat(found).isEqualTo("key");
  }

  @Test
  void should_Return_Key_If_Action_Name_Is_Equal_To_Given_Name() {
    when(action.getValue(NAME)).thenReturn("name");
    Object found = Actions.findActionKey("name", map);
    assertThat(found).isEqualTo("key");
  }

  @Test
  void should_Throw_Error_If_Key_Not_Found() {
    when(action.getValue(NAME)).thenReturn("name");
    ExpectedException.assertContainsMessage(ActionFailedException.class, () -> Actions.findActionKey("someName", map), "The action 'someName' is not available, available actions:['key']");
  }
}
