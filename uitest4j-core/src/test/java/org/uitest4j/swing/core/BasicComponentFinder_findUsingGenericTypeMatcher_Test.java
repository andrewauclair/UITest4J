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
import org.uitest4j.swing.exception.ComponentLookupException;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests for {@link BasicComponentFinder#find(GenericTypeMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicComponentFinder_findUsingGenericTypeMatcher_Test extends BasicComponentFinder_TestCase {
	@Test
	void should_Find_Component() {
		JButton foundButton = finder.find(new GenericTypeMatcher<>(JButton.class) {
			@Override
			protected boolean isMatching(@Nonnull JButton button) {
				return "A Button".equals(button.getText());
			}
		});
		assertThat(foundButton).isSameAs(window.button);
	}

	@Test
	void should_Throw_Error_If_GenericTypeMatcher_Never_Matches_Component() {
		assertThrows(ComponentLookupException.class, () -> finder.find(neverMatches(JButton.class)));
	}
}
