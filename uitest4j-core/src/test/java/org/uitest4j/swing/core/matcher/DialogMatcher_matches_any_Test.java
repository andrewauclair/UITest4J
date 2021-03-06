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

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JDialogs.dialog;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

/**
 * Tests for {@link DialogMatcher#matches(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogMatcher_matches_any_Test extends EDTSafeTestCase {
	@Test
	public void should_Always_Return_True_When_Matching_Any_Dialog() {
		DialogMatcher matcher = DialogMatcher.any();
		assertThat(matcher.matches(dialog().createNew())).isTrue();
	}

	@Test
	public void should_Return_False_If_Component_Is_Not_Dialog() {
		DialogMatcher matcher = DialogMatcher.any();
		assertThat(matcher.matches(textField().createNew())).isFalse();
	}
}
