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

import static java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.*;
import static org.uitest4j.swing.keystroke.KeyStrokeMapping.mapping;
import static org.uitest4j.swing.keystroke.KeyStrokeMappings.defaultMappings;

/**
 * Mapping between characters and {@code KeyStroke}s for locale {@code Locale.GERMAN}.
 *
 * @author Uli Schrempp
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProvider_de implements KeyStrokeMappingProvider {
	/**
	 * @return the mapping between characters and {@code KeyStroke}s for locale {@code Locale.GERMAN}.
	 */
	@Override
	@Nonnull
	public Collection<KeyStrokeMapping> keyStrokeMappings() {
		List<KeyStrokeMapping> mappings = new ArrayList<>(defaultMappings());
		mappings.add(mapping('0', VK_0, NO_MASK));
		mappings.add(mapping('=', VK_0, SHIFT_DOWN_MASK));
		mappings.add(mapping('}', VK_0, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('1', VK_1, NO_MASK));
		mappings.add(mapping('!', VK_1, SHIFT_DOWN_MASK));
		mappings.add(mapping('2', VK_2, NO_MASK));
		mappings.add(mapping('"', VK_2, SHIFT_DOWN_MASK));
		mappings.add(mapping('�', VK_2, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('3', VK_3, NO_MASK));
		mappings.add(mapping('�', VK_3, SHIFT_DOWN_MASK));
		mappings.add(mapping('�', VK_0, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('4', VK_4, NO_MASK));
		mappings.add(mapping('$', VK_4, SHIFT_DOWN_MASK));
		mappings.add(mapping('5', VK_5, NO_MASK));
		mappings.add(mapping('%', VK_5, SHIFT_DOWN_MASK));
		mappings.add(mapping('6', VK_6, NO_MASK));
		mappings.add(mapping('&', VK_6, SHIFT_DOWN_MASK));
		mappings.add(mapping('7', VK_7, NO_MASK));
		mappings.add(mapping('/', VK_7, SHIFT_DOWN_MASK));
		mappings.add(mapping('{', VK_7, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('8', VK_8, NO_MASK));
		mappings.add(mapping('(', VK_8, SHIFT_DOWN_MASK));
		mappings.add(mapping('[', VK_8, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('9', VK_9, NO_MASK));
		mappings.add(mapping(')', VK_9, SHIFT_DOWN_MASK));
		mappings.add(mapping(']', VK_9, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('a', VK_A, NO_MASK));
		mappings.add(mapping('A', VK_A, SHIFT_DOWN_MASK));
		mappings.add(mapping('b', VK_B, NO_MASK));
		mappings.add(mapping('B', VK_B, SHIFT_DOWN_MASK));
		mappings.add(mapping('^', VK_BACK_QUOTE, NO_MASK));
		mappings.add(mapping('�', VK_BACK_QUOTE, SHIFT_DOWN_MASK));
		mappings.add(mapping('<', VK_BACK_SLASH, NO_MASK));
		mappings.add(mapping('>', VK_BACK_SLASH, SHIFT_DOWN_MASK));
		mappings.add(mapping('|', VK_BACK_SLASH, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('c', VK_C, NO_MASK));
		mappings.add(mapping('C', VK_C, SHIFT_DOWN_MASK));
		mappings.add(mapping('+', VK_CLOSE_BRACKET, NO_MASK));
		mappings.add(mapping('*', VK_CLOSE_BRACKET, SHIFT_DOWN_MASK));
		mappings.add(mapping('~', VK_CLOSE_BRACKET, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping(',', VK_COMMA, NO_MASK));
		mappings.add(mapping(';', VK_COMMA, SHIFT_DOWN_MASK));
		mappings.add(mapping('d', VK_D, NO_MASK));
		mappings.add(mapping('D', VK_D, SHIFT_DOWN_MASK));
		mappings.add(mapping('', VK_DELETE, NO_MASK));
		mappings.add(mapping('e', VK_E, NO_MASK));
		mappings.add(mapping('E', VK_E, SHIFT_DOWN_MASK));
		mappings.add(mapping('�', VK_E, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('�', VK_EQUALS, NO_MASK));
		mappings.add(mapping('`', VK_EQUALS, SHIFT_DOWN_MASK));
		mappings.add(mapping('f', VK_F, NO_MASK));
		mappings.add(mapping('F', VK_F, SHIFT_DOWN_MASK));
		mappings.add(mapping('g', VK_G, NO_MASK));
		mappings.add(mapping('G', VK_G, SHIFT_DOWN_MASK));
		mappings.add(mapping('h', VK_H, NO_MASK));
		mappings.add(mapping('H', VK_H, SHIFT_DOWN_MASK));
		mappings.add(mapping('i', VK_I, NO_MASK));
		mappings.add(mapping('I', VK_I, SHIFT_DOWN_MASK));
		mappings.add(mapping('j', VK_J, NO_MASK));
		mappings.add(mapping('J', VK_J, SHIFT_DOWN_MASK));
		mappings.add(mapping('k', VK_K, NO_MASK));
		mappings.add(mapping('K', VK_K, SHIFT_DOWN_MASK));
		mappings.add(mapping('l', VK_L, NO_MASK));
		mappings.add(mapping('L', VK_L, SHIFT_DOWN_MASK));
		mappings.add(mapping('m', VK_M, NO_MASK));
		mappings.add(mapping('M', VK_M, SHIFT_DOWN_MASK));
		mappings.add(mapping('�', VK_M, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('?', VK_SLASH, SHIFT_DOWN_MASK));
		mappings.add(mapping('\\', VK_SLASH, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('n', VK_N, NO_MASK));
		mappings.add(mapping('N', VK_N, SHIFT_DOWN_MASK));
		mappings.add(mapping('o', VK_O, NO_MASK));
		mappings.add(mapping('O', VK_O, SHIFT_DOWN_MASK));
		mappings.add(mapping('p', VK_P, NO_MASK));
		mappings.add(mapping('P', VK_P, SHIFT_DOWN_MASK));
		mappings.add(mapping('.', VK_PERIOD, NO_MASK));
		mappings.add(mapping(':', VK_PERIOD, SHIFT_DOWN_MASK));
		mappings.add(mapping('q', VK_Q, NO_MASK));
		mappings.add(mapping('Q', VK_Q, SHIFT_DOWN_MASK));
		mappings.add(mapping('@', VK_Q, ALT_GRAPH_DOWN_MASK));
		mappings.add(mapping('r', VK_R, NO_MASK));
		mappings.add(mapping('R', VK_R, SHIFT_DOWN_MASK));
		mappings.add(mapping('s', VK_S, NO_MASK));
		mappings.add(mapping('S', VK_S, SHIFT_DOWN_MASK));
		mappings.add(mapping('-', VK_MINUS, NO_MASK));
		mappings.add(mapping('_', VK_MINUS, SHIFT_DOWN_MASK));
		mappings.add(mapping(' ', VK_SPACE, NO_MASK));
		mappings.add(mapping('t', VK_T, NO_MASK));
		mappings.add(mapping('T', VK_T, SHIFT_DOWN_MASK));
		mappings.add(mapping('u', VK_U, NO_MASK));
		mappings.add(mapping('U', VK_U, SHIFT_DOWN_MASK));
		mappings.add(mapping('v', VK_V, NO_MASK));
		mappings.add(mapping('V', VK_V, SHIFT_DOWN_MASK));
		mappings.add(mapping('w', VK_W, NO_MASK));
		mappings.add(mapping('W', VK_W, SHIFT_DOWN_MASK));
		mappings.add(mapping('x', VK_X, NO_MASK));
		mappings.add(mapping('X', VK_X, SHIFT_DOWN_MASK));
		mappings.add(mapping('y', VK_Y, NO_MASK));
		mappings.add(mapping('Y', VK_Y, SHIFT_DOWN_MASK));
		mappings.add(mapping('z', VK_Z, NO_MASK));
		mappings.add(mapping('Z', VK_Z, SHIFT_DOWN_MASK));
		return mappings;
	}
}
