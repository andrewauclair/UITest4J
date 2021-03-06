/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link JTreeDriver#nodeValue(javax.swing.JTree, String)}.
 *
 * @author Alex Ruiz
 */
class JTreeDriver_nodeValueAtRow_Test extends JTreeDriver_TestCase {
	@Test
	void should_Return_Node_Text() {
		String value = driver.nodeValue(tree, 1);
		assertThat(value).isEqualTo("branch1");
	}

	@Test
	void should_Throw_Error_If_Row_Is_Out_Of_Bounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> driver.nodeValue(tree, 100));
	}
}
