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

import org.opentest4j.AssertionFailedError;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.uitest4j.swing.image.ImageFileExtensions.PNG;

/**
 * Writes an image as a file in the file system.
 *
 * @author Alex Ruiz
 */
public class ImageFileWriter {
	/**
	 * Writes an image as a PNG file to the file system. If there is already a {@code File} present, its contents are
	 * discarded.
	 *
	 * @param image    a {@code BufferedImage} to be written.
	 * @param filePath the path of the image file to create.
	 * @return {@code false} if the image could not be saved.
	 * @throws IOException if an error occurs during writing.
	 */
	public boolean writeAsPng(@Nonnull BufferedImage image, @Nonnull String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new AssertionFailedError("Failed to create file: " + filePath);
			}
		}
		return ImageIO.write(image, PNG, file);
	}
}
