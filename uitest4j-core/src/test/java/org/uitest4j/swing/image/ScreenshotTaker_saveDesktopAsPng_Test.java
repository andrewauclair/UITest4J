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
package org.uitest4j.swing.image;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;

import static org.assertj.core.util.Files.temporaryFolderPath;
import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.assertions.Assertions.assertThat;
import static org.uitest4j.swing.image.RandomFileNameCreator.randomFileName;
import static org.uitest4j.swing.util.ImageReader.readImageFrom;

/**
 * Tests for {@link ScreenshotTaker#saveDesktopAsPng(String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ScreenshotTaker_saveDesktopAsPng_Test {
  private ScreenshotTaker taker;
  private String imagePath;

  @BeforeEach
  void setUp() {
    imagePath = concat(temporaryFolderPath(), randomFileName());
    taker = new ScreenshotTaker();
  }

  @AfterEach
  void tearDown() {
    File file = new File(imagePath);
    if (file.isFile()) {
      file.delete();
    }
  }

  @Test
  void should_Take_Screenshot_Of_Desktop_And_Save_It_In_Given_Path() throws Exception {
    taker.saveDesktopAsPng(imagePath);
    assertThat(readImageFrom(imagePath)).hasSize(Toolkit.getDefaultToolkit().getScreenSize());
  }
}
