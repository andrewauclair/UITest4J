/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.uitest4j.swing.hierarchy.ExistingHierarchy;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Collection;

/**
 * Obtains all the root AWT and Swing {@code Container}s of an {@link ExistingHierarchy}.
 *
 * @author Alex Ruiz
 */
class HierarchyRootsSource {
	@Nonnull
	Container[] existingHierarchyRoots() {
		Collection<? extends Container> roots = new ExistingHierarchy().roots();
		return roots.toArray(new Container[0]);
	}
}
