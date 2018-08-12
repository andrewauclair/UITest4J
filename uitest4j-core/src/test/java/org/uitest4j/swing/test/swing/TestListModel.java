/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.swing;

import javax.swing.*;

import java.util.Objects;

import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 * Simplified version of {@link DefaultListModel}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestListModel extends DefaultListModel {
	public TestListModel(Object... elements) {
		setElements(elements);
	}

	public void setElements(Object... elements) {
		clear();
		Objects.requireNonNull(elements);
		for (Object e : elements) {
			addElement(e);
		}
	}
}
