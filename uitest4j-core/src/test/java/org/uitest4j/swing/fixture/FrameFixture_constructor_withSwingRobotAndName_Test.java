/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.core.api.swing.SwingRobot;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.test.builder.JFrames.frame;

/**
 * Tests for {@link FrameFixture#FrameFixture(SwingRobot, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class FrameFixture_constructor_withSwingRobotAndName_Test extends SwingRobotBasedTestCase {
	@Test
	void should_Lookup_Showing_Frame_By_Name() {
		Frame target = frame().withName("frame").withTitle(getClass().getSimpleName()).createAndShow();
		FrameFixture fixture = new FrameFixture(robot, "frame");
		assertThat(fixture.robot()).isSameAs(robot);
		assertThat(fixture.target()).isSameAs(target);
	}

	@Test
	void should_Throw_Error_If_Frame_With_Matching_Name_Is_Not_Showing() {
		frame().withName("frame").createNew();
		assertThrows(ComponentLookupException.class, () -> new FrameFixture(robot, "frame"));
	}

	@Test
	void should_Throw_Error_If_A_Frame_With_Matching_Name_Is_Not_Found() {
		frame().withName("a frame").createNew();
		assertThrows(ComponentLookupException.class, () -> new FrameFixture(robot, "frame"));
	}
}
