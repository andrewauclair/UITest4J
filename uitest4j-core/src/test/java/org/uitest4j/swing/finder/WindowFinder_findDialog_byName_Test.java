/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.finder;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.fixture.DialogFixture;
import org.uitest4j.swing.test.swing.WindowLauncher.DialogToLaunch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link WindowFinder#findDialog(String)}.
 *
 * @author Alex Ruiz
 */
class WindowFinder_findDialog_byName_Test extends WindowFinder_TestCase {
	@Test
	void should_Find_Dialog() {
		clickLaunchDialogButton();
		DialogFixture found = WindowFinder.findDialog("dialog").using(robot);
		assertThat(found.target()).isInstanceOf(DialogToLaunch.class);
	}

	@Test
	void should_Find_Dialog_Before_Given_Timeout_Expires() {
		clickLaunchDialogButton();
		DialogFixture found = WindowFinder.findDialog("dialog").withTimeout(500, MILLISECONDS).using(robot);
		assertThat(found.target()).isInstanceOf(DialogToLaunch.class);
	}

	@Test
	void should_Find_Dialog_Before_Given_Timeout_In_Ms_Expires() {
		clickLaunchDialogButton();
		DialogFixture found = WindowFinder.findDialog("dialog").withTimeout(500).using(robot);
		assertThat(found.target()).isInstanceOf(DialogToLaunch.class);
	}

	@Test
	void should_Fail_If_Dialog_Not_Found() {
		assertThrows(WaitTimedOutError.class, () -> WindowFinder.findDialog("dialog").using(robot));
	}
}
