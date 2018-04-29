/*
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

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.internal.annotation.InternalApi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.io.File;
import java.util.Objects;

import static org.uitest4j.swing.core.matcher.JButtonMatcher.withText;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JFileChooserApproveButtonTextQuery.approveButtonTextFrom;
import static org.uitest4j.swing.driver.JFileChooserCancelButtonTextQuery.cancelButtonText;
import static org.uitest4j.swing.driver.JFileChooserSelectFileTask.setSelectedFile;
import static org.uitest4j.swing.driver.JFileChooserSelectFileTask.setSelectedFiles;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Supports functional testing of {@code JFileChooser}s.
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JFileChooserDriver extends JComponentDriver {

	/**
	 * Creates a new {@link JFileChooserDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JFileChooserDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Selects the given file in the {@code JFileChooser}.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @param file the file to select.
	 * @throws NullPointerException if the given file is {@code null}.
	 * @throws IllegalStateException if the {@code JFileChooser} is disabled.
	 * @throws IllegalStateException if the {@code JFileChooser} is not showing on the screen.
	 * @throws IllegalArgumentException if the {@code JFileChooser} can select directories only and the file to select is
	 *           not a directory.
	 * @throws IllegalArgumentException if the {@code JFileChooser} cannot select directories and the file to select is a
	 *           directory.
	 */
	@RunsInEDT
	public void selectFile(@Nonnull JFileChooser fileChooser, @Nonnull File file) {
		setSelectedFile(fileChooser, Objects.requireNonNull(file));
	}

	/**
	 * Selects the given file in the {@code JFileChooser}.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @param files the files to select.
	 * @throws NullPointerException if the given array of files is {@code null}.
	 * @throws IllegalArgumentException if the given array of files is empty.
	 * @throws IllegalStateException if this fixture's {@code JFileChooser} is disabled.
	 * @throws IllegalStateException if this fixture's {@code JFileChooser} is not showing on the screen.
	 * @throws IllegalStateException if this fixture's {@code JFileChooser} does not support multiple selection and there
	 *           is more than one file to select.
	 * @throws IllegalArgumentException if this fixture's {@code JFileChooser} can select directories only and any of the
	 *           files to select is not a directory.
	 * @throws IllegalArgumentException if this fixture's {@code JFileChooser} cannot select directories and any of the
	 *           files to select is a directory.
	 */
	public void selectFiles(@Nonnull JFileChooser fileChooser, @Nonnull File[] files) {
		Objects.requireNonNull(files);
		Objects.requireNonNull(files);

		if (files.length == 0) {
			throw new AssertionFailedError("files array should not be empty.");
		}
		for (File file : files) {
			Objects.requireNonNull(file);
		}
		setSelectedFiles(fileChooser, files);
	}

	/**
	 * Sets the current directory in the {@code JFileChooser} to the given one.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @param dir the directory to set as current.
	 * @throws IllegalStateException if the {@code JFileChooser} is disabled.
	 * @throws IllegalStateException if the {@code JFileChooser} is not showing on the screen.
	 */
	@RunsInEDT
	public void setCurrentDirectory(@Nonnull final JFileChooser fileChooser, @Nonnull final File dir) {
		execute(() -> {
			checkEnabledAndShowing(fileChooser);
			fileChooser.setCurrentDirectory(dir);
		});
	}

	/**
	 * Returns the text field where the user can enter the name of the file to select.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @return the found text field.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching text field could not be found.
	 */
	@RunsInEDT
	public JTextField fileNameTextBox(@Nonnull JFileChooser fileChooser) {
		return robot.finder().findByType(fileChooser, JTextField.class);
	}

	/**
	 * Finds and clicks the "Cancel" button in the given {@code JFileChooser}.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the "Cancel" button cannot be found.
	 * @throws IllegalStateException if the "Cancel" button is disabled.
	 * @throws IllegalStateException if the "Cancel" button is not showing on the screen.
	 */
	@RunsInEDT
	public void clickCancelButton(@Nonnull JFileChooser fileChooser) {
		checkInEdtEnabledAndShowing(fileChooser);
		click(cancelButton(fileChooser));
	}

	/**
	 * Finds the "Cancel" button in the given {@code JFileChooser}.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @return the found "Cancel" button.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the "Cancel" button cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton cancelButton(@Nonnull JFileChooser fileChooser) {
		return findButton(fileChooser, cancelButtonText());
	}

	/**
	 * Finds and clicks the "Approve" button in the given {@code JFileChooser}.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the "Approve" button cannot be found.
	 * @throws IllegalStateException if the "Approve" button is disabled.
	 * @throws IllegalStateException if the "Approve" button is not showing on the screen.
	 */
	@RunsInEDT
	public void clickApproveButton(@Nonnull JFileChooser fileChooser) {
		checkInEdtEnabledAndShowing(fileChooser);
		click(approveButton(fileChooser));
	}

	/**
	 * Finds the "Approve" button in the given {@code JFileChooser}.
	 *
	 * @param fileChooser the target {@code JFileChooser}.
	 * @return the found "Approve" button.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if the "Approve" button cannot be found.
	 */
	@RunsInEDT
	@Nonnull
	public JButton approveButton(@Nonnull JFileChooser fileChooser) {
		return findButton(fileChooser, approveButtonTextFrom(fileChooser));
	}

	@RunsInEDT
	@Nonnull
	private JButton findButton(@Nonnull JFileChooser fileChooser,
							   @Nullable String text) {
		return robot.finder().find(fileChooser, withText(text).andShowing());
	}
}
