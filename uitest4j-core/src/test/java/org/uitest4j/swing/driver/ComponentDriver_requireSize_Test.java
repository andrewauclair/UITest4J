/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

import java.awt.*;

import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;

/**
 * Tests for {@link ComponentDriver#requireSize(java.awt.Component, java.awt.Dimension)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentDriver_requireSize_Test extends ComponentDriver_TestCase {
	@Test
	void should_Pass_If_Actual_Size_Is_Equal_To_Expected() {
		Dimension expected = sizeOf(window.button);
		driver.requireSize(window.button, expected);
	}

	@Test
	void should_Fail_If_Actual_Size_Is_Not_Equal_To_Expected() {
		showWindow();
//    ExpectedException.assertContainsMessage(AssertionError.class, () -> driver.requireSize(window.button, new Dimension(0, 0)), "size", "expected:<...awt.Dimension[width=[0,height=0]]>", "but was:<");
		Dimension size = new Dimension(0, 0);
		ExpectedException.assertOpenTest4jError(() -> driver.requireSize(window.button, size), "Expected size of 'TestButton' to be [0, 0] but was [82, 26]", size, new Dimension(82, 26));
	}
}
