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
package org.assertj.swing.junit.runner;

import java.io.File;

import static org.uitest4j.swing.core.Settings.shouldPreserveScreenshots;

/**
 * Understands creation of the folder where screenshots of failed GUI tests will be saved to.
 *
 * @author Alex Ruiz
 */
public class ImageFolderCreator {

	private static final String FAILED_GUI_TESTS_FOLDER = "failed-gui-tests";

	private final FolderCreator folderCreator;

	public ImageFolderCreator() {
		this(new FolderCreator());
	}

	ImageFolderCreator(FolderCreator folderCreator) {
		this.folderCreator = folderCreator;
	}

	/**
	 * Creates the folder where to save screenshots of failing GUI tests. The name of the folder to create is
	 * 'failed-gui-tests'. If {@link org.uitest4j.swing.core.Settings#shouldPreserveScreenshots()} is <code>false</code>
	 * and the folder already exists, it is deleted and recreated again. If it is <code>true</code> the folder is created
	 * if it not yet exists.
	 *
	 * @return the created folder.
	 * @throws org.assertj.core.api.exception.RuntimeIOException if any error occurs when creating the folder.
	 */
	public File createImageFolder() {
		return folderCreator.createFolder(new File(System.getProperty("user.dir")), FAILED_GUI_TESTS_FOLDER, !shouldPreserveScreenshots());
	}
}
