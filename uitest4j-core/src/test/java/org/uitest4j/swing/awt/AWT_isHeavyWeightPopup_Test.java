/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.awt;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.awt.TestWindows.singletonWindowMock;
import static org.uitest4j.swing.test.builder.JDialogs.dialog;
import static org.uitest4j.swing.test.builder.JFrames.frame;

/**
 * Tests for {@link AWT#isHeavyWeightPopup(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
class AWT_isHeavyWeightPopup_Test extends EDTSafeTestCase {
	@Test
	void should_Return_False_If_Component_Is_Window() {
		Window w = singletonWindowMock();
		assertThat(AWT.isHeavyWeightPopup(w)).isFalse();
	}

	@Test
	void should_Return_False_If_Component_Is_Dialog() {
		JDialog d = dialog().createNew();
		assertThat(AWT.isHeavyWeightPopup(d)).isFalse();
	}

	@Test
	void should_Return_False_If_Component_Is_Frame() {
		JFrame f = frame().createNew();
		assertThat(AWT.isHeavyWeightPopup(f)).isFalse();
	}

	// TODO finish testing
}
