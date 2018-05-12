/*
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
package org.uitest4j.swing.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.event.InputEvent;

import static java.awt.event.InputEvent.*;

/**
 * Utility methods related to input modifiers.
 *
 * @author Alex Ruiz
 */
final class InputModifiers {
	static int unify(@Nullable int... modifiers) {
		if (modifiers == null) {
			return 0;
		}
		int unified = 0;
		if (modifiers.length > 0) {
			unified = modifiers[0];
			for (int i = 1; i < modifiers.length; i++) {
				unified |= modifiers[i];
			}
		}
		return unified;
	}
	
	static boolean isShiftDown(int modifiers) {
		return (modifiers & SHIFT_DOWN_MASK) != 0;
	}
	
	static boolean isControlDown(int modifiers) {
		return (modifiers & CTRL_DOWN_MASK) != 0;
	}
	
	static boolean isMetaDown(int modifiers) {
		return (modifiers & META_DOWN_MASK) != 0;
	}
	
	static boolean isAltDown(int modifiers) {
		return (modifiers & ALT_DOWN_MASK) != 0;
	}
	
	static boolean isAltGraphDown(int modifiers) {
		return (modifiers & ALT_GRAPH_DOWN_MASK) != 0;
	}
	
	static boolean modifiersMatch(@Nonnull InputEvent e, int modifiers) {
		return e.isAltDown() == isAltDown(modifiers) &&
				e.isAltGraphDown() == isAltGraphDown(modifiers) &&
				e.isControlDown() == isControlDown(modifiers) &&
				e.isMetaDown() == isMetaDown(modifiers) &&
				e.isShiftDown() == isShiftDown(modifiers);
	}
	
	private InputModifiers() {
	}
}
