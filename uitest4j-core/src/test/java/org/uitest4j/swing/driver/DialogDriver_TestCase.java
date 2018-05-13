/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestDialog;
import org.uitest4j.swing.test.swing.TestWindow;

/**
 * Base test case for {@link FrameDriver}.
 *
 * @author Alex Ruiz
 */
public abstract class DialogDriver_TestCase extends RobotBasedTestCase {
	TestDialog dialog;
	DialogDriver driver;

	@Override
	protected final void onSetUp() {
		TestWindow parent = TestWindow.createNewWindow(getClass());
		dialog = TestDialog.createNewDialog(parent);
		dialog.setName("TestDialog");
		driver = new DialogDriver(robot);
	}
}
