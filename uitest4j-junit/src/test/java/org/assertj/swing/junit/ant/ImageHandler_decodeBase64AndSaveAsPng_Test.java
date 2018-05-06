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
package org.assertj.swing.junit.ant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for <code>{@link ImageHandler#decodeBase64AndSaveAsPng(String, String)}</code>.
 * 
 * @author Alex Ruiz
 */
class ImageHandler_decodeBase64AndSaveAsPng_Test extends ImageHandler_TestCase {
  private static Collection<Object[]> emptyOrNull() {
    return newArrayList(new Object[][] { { "" }, { null } });
  }

  @ParameterizedTest
  @MethodSource("emptyOrNull")
  void shouldReturnEmptyStringIfEncodedImageIsEmptyOrNullWhenDecodingAndSavingImage(String val) {
    assertThat(ImageHandler.decodeBase64AndSaveAsPng(val, "somePath"));
  }

  @ParameterizedTest
  @MethodSource("emptyOrNull")
  void shouldReturnEmptyStringIfFilePathIsEmptyOrNullWhenDecodingAndSavingImage(String val) {
    assertThat(ImageHandler.decodeBase64AndSaveAsPng("someImage", val));
  }
}
