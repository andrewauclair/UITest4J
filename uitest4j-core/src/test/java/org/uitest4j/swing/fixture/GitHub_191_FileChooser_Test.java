/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.uitest4j.swing.edt.GuiActionRunner;
import org.uitest4j.swing.finder.JFileChooserFinder;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Test for <a href="https://github.com/joel-costigliola/assertj-swing/issues/191">github.com - assertj-swing #191</a>
 */
public class GitHub_191_FileChooser_Test extends RobotBasedTestCase {
	private TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected FrameFixture window;

	@Override
	protected void onSetUp() {
		FileChooserFrame mainFrame = GuiActionRunner.execute(FileChooserFrame::new);
		window = new FrameFixture(robot, mainFrame);
	}

	@Test
	@Disabled("Doesn't work on JUnit 5 and Github 191 looks to be open still")
	void openFileChooser() throws IOException {
		selectFile();
	}

	@Test
	@Disabled("Doesn't work on JUnit 5 and Github 191 looks to be open still")
	void openFileChooserAgain() throws IOException {
		selectFile();
	}

	private void selectFile() throws IOException {
		window.button().click();
		JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser()
				.using(robot);

		File tempFile = temporaryFolder.newFile("issue-191.txt");
		System.out.println(tempFile.getAbsolutePath());
		fileChooser.setCurrentDirectory(temporaryFolder.getRoot());
		fileChooser.selectFile(tempFile);
		fileChooser.approve();

		window.textBox().requireText(tempFile.getAbsolutePath());
	}

	private class FileChooserFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		FileChooserFrame() {
			setSize(200, 100);
			setTitle(getClass().getCanonicalName());
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLayout(new FlowLayout());

			JTextField textField = new JTextField();
			textField.setPreferredSize(new Dimension(200, 20));
			JButton button = new JButton("FileChooser");
			JFrame parentFrame = this;
			button.addActionListener(e -> {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(parentFrame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			});

			add(textField);
			add(button);
			setVisible(true);
		}
	}
}