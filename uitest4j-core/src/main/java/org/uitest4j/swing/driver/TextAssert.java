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
package org.uitest4j.swing.driver;

import org.opentest4j.AssertionFailedError;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static org.uitest4j.swing.util.Strings.areEqualOrMatch;

/**
 * Assertion methods related to text.
 *
 * @author Alex Ruiz
 */
class TextAssert {//extends AbstractCharSequenceAssert<TextAssert, String> {
	private final String actual;
	private String description = "";

	static @Nonnull
	TextAssert assertThat(@Nullable String s) {
		return new TextAssert(s);
	}

	static @Nonnull
	TextAssert verifyThat(@Nullable String s) {
		return new TextAssert(s);
	}

	TextAssert(@Nullable String actual) {
//		super(actual, TextAssert.class);
		this.actual = actual != null ? actual : "";
	}

	TextAssert as(String description) {
		this.description = description;
		return this;
	}

	//	@Override
	public TextAssert matches(Pattern pattern) {
		if (!pattern.matcher(actual).matches()) {
			String desc = "";
			if (description != null) {
				desc = "[" + description + "] ";
				if (desc.equals("[] ")) {
					desc = "";
				}
			}
			throw new AssertionFailedError(String.format("%sExpecting: %s to match pattern: %s", desc, actual, pattern));
		}
		return this;
	}

	void isEqualOrMatches(@Nullable String s) {
		if (areEqualOrMatch(s, actual)) {
			return;
		}
		String desc = "";
		if (description != null) {
			desc = "[" + description + "] ";
			if (desc.equals("[] ")) {
				desc = "";
			}
		}
		throw new AssertionFailedError(String.format("%sExpecting: %s to match pattern: %s", desc, actual, s));
	}
}
