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

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;

import static org.uitest4j.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;

/**
 * Base test case for {@link ComponentDriver} that verify that a {@code JPopupMenu} is invoked by a {@code Component}.
 *
 * @author Alex Ruiz
 */
abstract class ComponentDriver_invokePopup_TestCase extends ComponentDriver_TestCase {
	JPopupMenu popupMenu;

	@RunsInEDT
	@Override
	void extraSetUp() {
		popupMenu = createAndSetPopupMenu(window.textField, "Hello");
	}
}
