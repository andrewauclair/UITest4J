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

import static java.io.File.separator;
import static org.apache.tools.ant.util.FileUtils.delete;

/**
 * Understands creation of folders.
 *
 * @author Alex Ruiz
 */
class FolderCreator {

	File createFolder(File parent, String name, boolean deleteIfExists) {
		try {
			String canonicalPath = parent.getCanonicalPath();
			File imageFolder = new File(canonicalPath + separator + name);
			if (deleteIfExists) {
				delete(imageFolder);
			}
			imageFolder.mkdir();
			return imageFolder;
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to create directory '" + name + "'", e);
		}
	}
}
