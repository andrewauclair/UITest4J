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
import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.fixture.FrameFixture;
import org.uitest4j.swing.test.swing.WindowLauncher.WindowToLaunch;

import javax.annotation.Nonnull;
import javax.swing.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.query.ComponentShowingQuery.isShowing;

/**
 * Tests for {@link WindowFinder#findFrame(org.uitest4j.swing.core.GenericTypeMatcher)}.
 *
 * @author Alex Ruiz
 */
class WindowFinder_findFrame_withMatcher_Test extends WindowFinder_TestCase {
	private MyMatcher matcher;

	@Override
	void extraSetUp() {
		matcher = new MyMatcher();
	}

	@Test
	void should_Find_Frame() {
		clickLaunchFrameButton();
		FrameFixture found = WindowFinder.findFrame(matcher).using(robot);
		assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
	}

	@Test
	void should_Find_Frame_Before_Given_Timeout_Expires() {
		clickLaunchFrameButton();
		FrameFixture found = WindowFinder.findFrame(matcher).withTimeout(500, MILLISECONDS).using(robot);
		assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
	}

	@Test
	void should_Find_Frame_Before_Given_Timeout_In_Ms_Expires() {
		clickLaunchFrameButton();
		FrameFixture found = WindowFinder.findFrame(matcher).withTimeout(500).using(robot);
		assertThat(found.target()).isInstanceOf(WindowToLaunch.class);
	}

	@Test
	void should_Fail_If_Frame_Not_Found() {
		assertThrows(WaitTimedOutError.class, () -> WindowFinder.findFrame(matcher).using(robot));
	}

	private static class MyMatcher extends GenericTypeMatcher<JFrame> {
		private MyMatcher() {
			super(JFrame.class);
		}

		@Override
		protected boolean isMatching(@Nonnull JFrame frame) {
			return "frame".equals(frame.getName()) && isShowing(frame);
		}
	}
}
