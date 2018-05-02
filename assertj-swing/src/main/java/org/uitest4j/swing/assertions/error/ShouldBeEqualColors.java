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
package org.uitest4j.swing.assertions.error;

import org.assertj.core.data.Offset;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.uitest4j.swing.assertions.data.Point;
import org.uitest4j.swing.assertions.data.RgbColor;

/**
 * Creates an error message indicating that an assertion that verifies that a two colors are equal at a given point
 * fails.
 *
 * @author Yvonne Wang
 */
public class ShouldBeEqualColors extends BasicErrorMessageFactory {

	public ShouldBeEqualColors(RgbColor expected, RgbColor actual, Point point, Offset<?> offset) {
		super("expected:<%s> but was:<%s> at:<%s> within offset:<%s>", expected, actual, point, offset.value);
	}

	public static String message(RgbColor expected, RgbColor actual, Point point, int offset) {
		return String.format("expected:<%s> but was:<%s> at:<%s> within offset:<%s>", expected, actual, point, offset);
	}
}
