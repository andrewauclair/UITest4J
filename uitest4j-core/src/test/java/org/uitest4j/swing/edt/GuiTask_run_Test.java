/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.edt;

import org.junit.jupiter.api.Test;
import org.uitest4j.exception.ActionFailedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link GuiTask#run()}.
 *
 * @author Alex Ruiz
 */
class GuiTask_run_Test {
	@Test
	void should_Throw_Error_If_Not_Called_In_EDT() {
		assertThrows(ActionFailedException.class, () -> new GuiTask() {
			@Override
			protected void executeInEDT() {
			}
		}.run());
	}
}
