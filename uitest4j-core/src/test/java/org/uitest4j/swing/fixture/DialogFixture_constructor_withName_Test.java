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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.lock.ScreenLock;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.uitest4j.swing.test.builder.JDialogs.dialog;

/**
 * Tests for {@link DialogFixture#DialogFixture(String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class DialogFixture_constructor_withName_Test extends EDTSafeTestCase {
	private DialogFixture fixture;

	@AfterEach
	void tearDown() {
		if (fixture != null) {
			fixture.cleanUp();
		}
	}

	@Test
	void should_Lookup_Showing_Dialog_By_Name_Using_New_Robot() {
		Dialog target = dialog().withName("dialog").withTitle(getClass().getSimpleName()).createAndShow();
		fixture = new DialogFixture("dialog");
		assertThat(fixture.robot()).isNotNull();
		assertThat(fixture.target()).isSameAs(target);
	}

	@Test
	void should_Throw_Error_If_Dialog_With_Matching_Name_Is_Not_Found() {
		assertAll(
				() -> assertThrows(ComponentLookupException.class, () -> new DialogFixture("dialog")),
				() -> assertFalse(ScreenLock.instance().acquired())
		);
	}
}
