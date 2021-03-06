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
import org.uitest4j.swing.fixture.FrameFixture;
import org.uitest4j.swing.test.swing.WindowLauncher.WindowToLaunch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link WindowFinder#findFrame(Class)}.
 *
 * @author Alex Ruiz
 */
class WindowFinder_findFrame_byType_Test extends WindowFinder_TestCase {
	@Test
	void should_Find_Frame() {
		clickLaunchFrameButton();
		FrameFixture found = WindowFinder.findFrame(WindowToLaunch.class).using(robot);
		assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
	}

	@Test
	void should_Find_Frame_Before_Given_Timeout_Expires() {
		clickLaunchFrameButton();
		FrameFixture found = WindowFinder.findFrame(WindowToLaunch.class).withTimeout(500, MILLISECONDS).using(robot);
		assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
	}

	@Test
	void should_Find_Frame_Before_Given_Timeout_In_Ms_Expires() {
		clickLaunchFrameButton();
		FrameFixture found = WindowFinder.findFrame(WindowToLaunch.class).withTimeout(500).using(robot);
		assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
	}

	@Test
	void should_Fail_If_Frame_Not_Found() {
		assertThrows(WaitTimedOutError.class, () -> WindowFinder.findFrame(WindowToLaunch.class).using(robot));
	}
}
