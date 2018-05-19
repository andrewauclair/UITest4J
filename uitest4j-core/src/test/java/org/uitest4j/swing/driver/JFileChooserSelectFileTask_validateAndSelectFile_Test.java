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
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.*;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.assertj.core.util.Files.temporaryFolder;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JFileChooserSelectFileTask#setSelectedFile(JFileChooser, File)}.
 *
 * @author Yvonne Wang
 */
public class JFileChooserSelectFileTask_validateAndSelectFile_Test extends SwingRobotBasedTestCase {
	private JFileChooser fileChooser;
	private File fileToSelect;

	@Override
	protected void onSetUp() {
		fileToSelect = newTemporaryFile();
		MyWindow window = MyWindow.createNew();
		robot.showWindow(window);
		fileChooser = window.fileChooser;
	}

	@Override
	protected void onTearDown() {
		fileToSelect.delete();
	}

	@Test
	public void should_Select_File() {
		JFileChooserSelectFileTask.setSelectedFile(fileChooser, fileToSelect);
		robot.waitForIdle();
		assertThat(selectedFile()).isEqualTo(fileToSelect);
	}

	@RunsInEDT
	private File selectedFile() {
		return selectedFileOf(fileChooser);
	}

	@RunsInEDT
	private static File selectedFileOf(final JFileChooser fileChooser) {
		return execute(fileChooser::getSelectedFile);
	}

	private static class MyWindow extends TestWindow {
		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		final JFileChooser fileChooser = new JFileChooser(temporaryFolder());

		private MyWindow() {
			super(JFileChooserSelectFileTask.class);
			add(fileChooser);
		}
	}
}
