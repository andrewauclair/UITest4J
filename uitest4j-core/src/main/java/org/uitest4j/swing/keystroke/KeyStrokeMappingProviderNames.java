/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.keystroke;

import org.uitest4j.swing.util.OSFamily;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * A generated name for a {@link KeyStrokeMappingProvider} to use, based on operating system family and locale.
 *
 * @author Alex Ruiz
 */
class KeyStrokeMappingProviderNames implements Iterable<String> {
	private final String osFamily;
	private final String language;
	private final String country;

	static KeyStrokeMappingProviderNames generateNamesFrom(@Nonnull OSFamily osFamily, @Nonnull Locale locale) {
		return new KeyStrokeMappingProviderNames(osFamily, locale);
	}

	private KeyStrokeMappingProviderNames(@Nonnull OSFamily osFamily, @Nonnull Locale locale) {
		this.osFamily = osFamily.key();
		language = locale.getLanguage();
		country = locale.getCountry();
	}

	@Override
	public Iterator<String> iterator() {
		return new NameIterator(osFamily, language, country);
	}

	private static class NameIterator implements Iterator<String> {
		private static final String PREFIX = KeyStrokeMappingProvider.class.getName();

		private static final String DELIMETER = "_";

		private final String osFamily;
		private final String language;
		private final String country;

		private State state;

		private enum State {
			FULL_NAME, WITHOUT_COUNTRY, LANGUAGE_ONLY, END
		}

		NameIterator(String osFamily, String language, String country) {
			this.osFamily = osFamily;
			this.language = language;
			this.country = country;
			state = State.FULL_NAME;
		}

		@Override
		public String next() {
			switch (state) {
				case FULL_NAME:
					state = State.WITHOUT_COUNTRY;
					return String.join(DELIMETER, PREFIX, osFamily, language, country);
				case WITHOUT_COUNTRY:
					state = State.LANGUAGE_ONLY;
					return String.join(DELIMETER, PREFIX, osFamily, language);
				case LANGUAGE_ONLY:
					state = State.END;
					return String.join(DELIMETER, PREFIX, language);
				default:
					throw new NoSuchElementException("There are no more names to generate");
			}
		}

		@Override
		public boolean hasNext() {
			return state != State.END;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("This iterator does not support 'remove'");
		}
	}
}
