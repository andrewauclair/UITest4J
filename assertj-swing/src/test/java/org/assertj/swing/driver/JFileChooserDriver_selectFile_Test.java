/**
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
package org.assertj.swing.driver;

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.assertj.core.util.Files.newTemporaryFolder;
import static org.assertj.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JFileChooserDriver#selectFile(JFileChooser, File)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JFileChooserDriver_selectFile_Test extends JFileChooserDriver_TestCase {
  @Test
  void should_Select_File() {
    showWindow();
    File temporaryFile = newTemporaryFile();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      File selectedFile = selectedFileIn(fileChooser);
      assertThat(selectedFile).isSameAs(temporaryFile);
    } finally {
      temporaryFile.delete();
    }
  }

  @RunsInEDT
  private static File selectedFileIn(final JFileChooser fileChooser) {
    return execute(fileChooser::getSelectedFile);
  }

  @Test
  void should_Throw_Error_If_JFileChooser_Is_Disabled() {
    disableFileChooser();
    ExpectedException.assertIllegalStateIsDisabledComponent(() -> driver.selectFile(fileChooser, fakeFile()));
  }

  @Test
  void should_Throw_Error_If_JFileChooser_Is_Not_Showing_On_The_Screen() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectFile(fileChooser, fakeFile()));
  }

  @Test
  void should_Throw_Error_When_Selecting_File_While_JFileChooser_Can_Only_Select_Folders() {
    makeFileChooserSelectDirectoriesOnly();
    showWindow();
    ExpectedException.assertContainsMessage(IllegalArgumentException.class, () ->driver.selectFile(fileChooser, fakeFile()), "the file chooser can only open directories");
  }

  @Test
  void should_Throw_Error_When_Selecing_Folder_While_JFileChooser_Can_Only_Select_Files() {
    File temporaryFolder = newTemporaryFolder();
    makeFileChooserSelectFilesOnly();
    showWindow();
    try {
      ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.selectFile(fileChooser, temporaryFolder), "the file chooser can only open files");
    } finally {
      temporaryFolder.delete();
    }
  }
}
