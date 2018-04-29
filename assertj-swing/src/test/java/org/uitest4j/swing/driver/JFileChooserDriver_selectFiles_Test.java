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
package org.uitest4j.swing.driver;

import org.assertj.swing.test.ExpectedException;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.assertj.core.util.Files.newTemporaryFolder;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JFileChooserDriver#selectFiles(javax.swing.JFileChooser, java.io.File[])}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_selectFiles_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_Throw_Error_If_JFileChooser_Is_Disabled() {
    ExpectedException.assertIllegalStateIsNotShowingComponent(() -> driver.selectFiles(fileChooser, array(fakeFile())));
  }

  @Test
  public void should_Throw_Error_When_Selecting_Files_And_JFileChooser_Cannot_Handle_Multiple_Selection() {
    disableMultipleSelection();
    showWindow();
    ExpectedException.assertContainsMessage(IllegalStateException.class, () -> driver.selectFiles(fileChooser, array(new File("Fake1"), new File("Fake2"))), "Expecting file chooser");
  }

  @RunsInEDT
  private void disableMultipleSelection() {
    setMultipleSelectionEnabled(fileChooser, false);
    robot.waitForIdle();
  }

  @Test
  public void should_Throw_Error_When_Selecing_Folders_While_JFileChooser_Can_Only_Select_Files() {
    enableMultipleSelection();
    TemporaryFolderAndFile folderAndFile = new TemporaryFolderAndFile();
    makeFileChooserSelectDirectoriesOnly();
    showWindow();
    try {
      ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.selectFiles(fileChooser, folderAndFile.contents()), "the file chooser can only open directories");
    } finally {
      folderAndFile.delete();
    }
  }

  @Test
  public void should_Throw_Error_When_Selecting_Files_While_JFileChooser_Can_Only_Select_Folders() {
    enableMultipleSelection();
    TemporaryFolderAndFile folderAndFile = new TemporaryFolderAndFile();
    makeFileChooserSelectFilesOnly();
    showWindow();
    try {
      ExpectedException.assertContainsMessage(IllegalArgumentException.class, () -> driver.selectFiles(fileChooser, folderAndFile.contents()), "the file chooser can only open files");
    } finally {
      folderAndFile.delete();
    }
  }

  @Test
  public void should_Select_Single_Given_File_When_JFileChooser_Cannot_Handle_Multiple_Selection() {
    disableMultipleSelection();
    File temporaryFile = newTemporaryFile();
    showWindow();
    try {
      driver.selectFiles(fileChooser, array(temporaryFile));
      File[] selectedFiles = selectedFilesIn(fileChooser);
      assertThat(selectedFiles).containsOnly(temporaryFile);
    } finally {
      temporaryFile.delete();
    }
  }

  @Test
  public void should_Select_Files() {
    // TODO(Alex): Test in Windows
    enableMultipleSelection();
    TemporaryFolderAndFile folderAndFile = new TemporaryFolderAndFile();
    makeFileChooserSelectFilesAndDirectories();
    showWindow();
    try {
      driver.selectFiles(fileChooser, folderAndFile.contents());
      File[] selectedFiles = selectedFilesIn(fileChooser);
      assertThat(selectedFiles).containsOnly(folderAndFile.folder, folderAndFile.file);
    } finally {
      folderAndFile.delete();
    }
  }

  @RunsInEDT
  private void enableMultipleSelection() {
    setMultipleSelectionEnabled(fileChooser, true);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setMultipleSelectionEnabled(final JFileChooser fileChooser, final boolean b) {
    execute(() -> fileChooser.setMultiSelectionEnabled(b));
  }

  @RunsInEDT
  private static File[] selectedFilesIn(final JFileChooser fileChooser) {
    return execute(() -> fileChooser.getSelectedFiles());
  }

  private static class TemporaryFolderAndFile {
    final File folder;
    final File file;

    TemporaryFolderAndFile() {
      folder = newTemporaryFolder();
      file = newTemporaryFile();
    }

    File[] contents() {
      return array(folder, file);
    }

    void delete() {
      folder.delete();
      file.delete();
    }
  }
}
