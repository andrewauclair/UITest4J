/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.javafx.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.test.ExpectedException;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andrew Auclair
 */
class BasicNodeFinder_Component_findByName_Test extends BasicNodeFinder_Component_TestCase {
	@Test
	@Disabled("Not sure how to do the swing/javafx crossover yet")
	void should_Find_Component() {
//		Component button = finder.findByName("button");
//		assertThat(button).isSameAs(window.button);
	}

	@Test
	@Disabled("Not sure how to do the swing/javafx crossover yet")
	void should_Throw_Error_If_Component_Not_Found() {
		ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> finder.findByName("list"), "name='list'");
	}
}
