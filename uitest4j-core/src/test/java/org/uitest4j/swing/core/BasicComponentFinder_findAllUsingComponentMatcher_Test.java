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
import java.awt.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BasicComponentFinder#findAll(ComponentMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicComponentFinder_findAllUsingComponentMatcher_Test extends BasicComponentFinder_TestCase {
	@Test
	void should_Return_All_Matching_Components() {
		Collection<Component> found = finder.findAll(c -> c instanceof JTextField);
		assertThat(found).containsOnly(window.textField1, window.textField2);
	}
}
