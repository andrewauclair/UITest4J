/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LabelMatcher#matches(java.awt.Component)}.
 *
 * <p>
 * The {@link LabelMatcher} is created through {@link LabelMatcher#LabelMatcher(String, Class)}.
 * </p>
 *
 * @author Alex Ruiz
 */
class LabelMatcher_matches_byLabelAndType_Test extends LabelMatcher_TestCase {
	@Test
	void should_Return_True_If_Label_And_Type_Match() {
		window.display();
		LabelMatcher matcher = new LabelMatcher(LABEL_TEXT, JButton.class);
		assertThat(matcher.matches(window.buttonLabel)).isTrue();
	}
}
