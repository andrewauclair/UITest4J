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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.uitest4j.swing.test.data.BooleanProvider;

import javax.annotation.Nonnull;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link AbstractComponentMatcher#requireShowing(boolean)} and
 * {@link AbstractComponentMatcher#requireShowing()}.
 *
 * @author Alex Ruiz
 */
class AbstractComponentMatcher_requireShowing_Test {
	@Nonnull
	private static Collection<Object[]> booleans() {
		return newArrayList(BooleanProvider.booleans());
	}

	@ParameterizedTest
	@MethodSource("booleans")
	void should_Require_Showing_As_Specified_In_Setter(boolean requireShowing) {
		AbstractComponentMatcher matcher = new ConcreteComponentMatcher();
		matcher.requireShowing(requireShowing);
		assertThat(matcher.requireShowing()).isEqualTo(requireShowing);
	}
}
