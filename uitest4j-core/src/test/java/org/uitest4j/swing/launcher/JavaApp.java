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
package org.uitest4j.swing.launcher;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.SwingUtilities.invokeLater;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * An application with a "main" method that shows a {@code JFrame}.
 *
 * @author Yvonne Wang
 */
public class JavaApp {
	private static List<ArgumentObserver> argumentObservers = newArrayList();

	public static void main(String[] args) {
		for (ArgumentObserver observer : argumentObservers) {
			observer.arguments(args);
		}
		invokeLater(() -> {
			JFrame frame = new JFrame("Java Application");
			frame.setPreferredSize(new Dimension(200, 200));
			frame.pack();
			frame.setVisible(true);
		});
	}

	public static void add(ArgumentObserver observer) {
		argumentObservers.add(observer);
	}

	public static void remove(ArgumentObserver observer) {
		argumentObservers.remove(observer);
	}

	interface ArgumentObserver {
		void arguments(String[] args);
	}
}
