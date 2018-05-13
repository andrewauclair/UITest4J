/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.assertions;

import java.awt.image.BufferedImage;

/**
 * Entry point for assertion methods for different data types (including swing types). Each method in this class is a
 * static factory for the type-specific assertion objects. The purpose of this class is to make test code more readable.
 */
public class Assertions {
	/**
	 * Creates a new instance of <code>{@link ImageAssert}</code>.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static ImageAssert assertThat(BufferedImage actual) {
		return new ImageAssert(actual);
	}
}
