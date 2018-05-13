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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.internal.annotation.IORuntimeException;
import org.uitest4j.swing.util.RobotFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.uitest4j.swing.image.TestImageFileWriters.newImageFileWriterMock;

/**
 * Tests for {@link ScreenshotTaker#saveImage(BufferedImage, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ScreenshotTaker_saveImage_Test {
	private BufferedImage image;
	private String path;
	private ImageFileWriter writer;
	private IOException error;
	private ScreenshotTaker taker;

	@BeforeEach
	void setUp() {
		image = mock(BufferedImage.class);
		path = "image.png";
		writer = newImageFileWriterMock();
		error = new IOException("On Purpose");
		taker = new ScreenshotTaker(writer, new RobotFactory());
	}

	@Test
	void should_Throw_Wrapped_Exception_Thrown_When_Writing_Image_To_File() throws IOException {
		when(writer.writeAsPng(image, path)).thenThrow(error);
		Throwable exception = assertThrows(IORuntimeException.class, () -> taker.saveImage(image, path));
		assertThat(exception.getCause()).isEqualTo(error);
	}
}
