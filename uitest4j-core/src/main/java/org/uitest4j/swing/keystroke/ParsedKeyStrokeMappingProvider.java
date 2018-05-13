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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static org.uitest4j.swing.keystroke.KeyStrokeMappings.defaultMappings;

/**
 * {@link KeyStrokeMappingProvider} created by parsing a text file containing all the key mappings.
 *
 * @author Alex Ruiz
 * @see KeyStrokeMappingsParser
 */
class ParsedKeyStrokeMappingProvider implements KeyStrokeMappingProvider {
	private final List<KeyStrokeMapping> mappings = new ArrayList<>();

	ParsedKeyStrokeMappingProvider(@Nonnull List<KeyStrokeMapping> mappings) {
		this.mappings.addAll(defaultMappings());
		this.mappings.addAll(mappings);
	}

	/**
	 * Returns the mapping of characters and {@link javax.swing.KeyStroke}s. The mappings are obtained from a text file. In addition,
	 * this provider will automatically add mappings for following keys:
	 * <ul>
	 * <li>Backspace</li>
	 * <li>Delete</li>
	 * <li>Enter</li>
	 * <li>Escape</li>
	 * <li>Tab</li>
	 * </ul>
	 *
	 * @return the mapping of characters and {@code KeyStroke}s.
	 */
	@Override
	@Nonnull
	public Collection<KeyStrokeMapping> keyStrokeMappings() {
		return unmodifiableList(mappings);
	}
}
