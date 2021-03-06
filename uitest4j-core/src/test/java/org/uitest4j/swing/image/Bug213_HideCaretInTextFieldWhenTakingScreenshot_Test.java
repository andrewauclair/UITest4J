/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.image;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.SequentialEDTSafeTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static org.uitest4j.swing.assertions.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=213">Bug 213</a>.
 *
 * @author Alex Ruiz
 */
public class Bug213_HideCaretInTextFieldWhenTakingScreenshot_Test extends SequentialEDTSafeTestCase {
	private ScreenshotTaker screenshotTaker;
	private MyWindow window;

	@Override
	protected void onSetUp() {
		screenshotTaker = new ScreenshotTaker();
		window = MyWindow.createAndShow();
	}

	@Override
	protected void onTearDown() {
		window.destroy();
	}

	@Test
	void should_Hide_Caret_In_JTextField_When_Taking_Screenshot() throws InterruptedException {
		// wait a second so that the window has been fully shown
		Thread.sleep(1000);

		BufferedImage currentImage = screenshotTaker.takeScreenshotOf(window);
		for (int i = 0; i < 100; i++) {
			BufferedImage newImage = screenshotTaker.takeScreenshotOf(window);
			assertThat(newImage).isEqualTo(currentImage);
		}
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createAndShow() {
			return execute(() -> display(new MyWindow()));
		}

		final JTextField textField = new JTextField(20);
		final JButton button = new JButton("Hello");

		private MyWindow() {
			super(Bug213_HideCaretInTextFieldWhenTakingScreenshot_Test.class);
			addComponents(textField, button);
		}
	}
}
