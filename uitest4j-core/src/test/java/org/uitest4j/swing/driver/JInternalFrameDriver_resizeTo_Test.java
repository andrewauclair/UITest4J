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
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.awt.FluentDimension;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;

/**
 * Tests for {@link JInternalFrameDriver#resizeTo(javax.swing.JInternalFrame, java.awt.Dimension)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_resizeTo_Test extends JInternalFrameDriver_TestCase {
	@Test
	public void should_Resize_JInternalFrame() {
		showWindow();
		Dimension newSize = internalFrameSize().addToWidth(20).addToHeight(40);
		driver.resizeTo(internalFrame, newSize);
		assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
	}

	@RunsInEDT
	private FluentDimension internalFrameSize() {
		return new FluentDimension(sizeOf(internalFrame));
	}
}
