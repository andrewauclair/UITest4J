/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core.matcher;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JFrames.frame;

/**
 * Tests for {@link FrameMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameMatcher_matches_byTitle_Test extends EDTSafeTestCase {
	@Test
	public void should_Return_True_If_Title_Is_Equal_To_Expected() {
		FrameMatcher matcher = FrameMatcher.withTitle("Hello");
		JFrame frame = frame().withTitle("Hello").createNew();
		assertThat(matcher.matches(frame)).isTrue();
	}

	@Test
	public void should_Return_True_If_Title_Matches_Pattern() {
		FrameMatcher matcher = FrameMatcher.withTitle("He.*");
		JFrame frame = frame().withTitle("Hello").createNew();
		assertThat(matcher.matches(frame)).isTrue();
	}

	@Test
	public void should_Return_False_If_Title_Is_Not_Equal_To_Expected() {
		FrameMatcher matcher = FrameMatcher.withTitle("Hello");
		JFrame frame = frame().withTitle("Bye").createNew();
		assertThat(matcher.matches(frame)).isFalse();
	}
}
