/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.awt;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

/**
 * Tests for {@link AWT#centerOf(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
class AWT_centerOfComponent_Test extends EDTSafeTestCase {
	@Test
	void should_Return_Center_Position() {
		Component c = textField().withColumns(20).createNew();
		setComponentSize(c, 80, 60);
		Point center = AWT.centerOf(c);
		assertThat(center.x).isEqualTo(40);
		assertThat(center.y).isEqualTo(30);
	}

	@RunsInEDT
	private static void setComponentSize(final Component c, final int width, final int height) {
		execute(() -> c.setSize(width, height));
	}
}
