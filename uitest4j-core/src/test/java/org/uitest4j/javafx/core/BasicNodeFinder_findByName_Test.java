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

import javafx.scene.Node;
import org.junit.jupiter.api.Test;
import org.uitest4j.javafx.exception.NodeLookupException;
import org.uitest4j.swing.test.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andrew Auclair
 */
class BasicNodeFinder_findByName_Test extends BasicNodeFinder_TestCase {
	@Test
	void finds_node_by_name() {
		Node button = finder.findByName("button");
		assertThat(button).isSameAs(stage.button);
	}

	@Test
	void throws_error_if_node_is_not_found_by_name() {
		ExpectedException.assertContainsMessage(NodeLookupException.class, () -> finder.findByName("list"), "name='list'");
	}
}
