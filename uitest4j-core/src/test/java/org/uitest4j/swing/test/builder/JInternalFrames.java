/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.builder;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JInternalFrame}s.
 *
 * @author Alex Ruiz
 */
public final class JInternalFrames {
	private JInternalFrames() {
	}

	public static JInternalFrameFactory internalFrame() {
		return new JInternalFrameFactory();
	}

	public static class JInternalFrameFactory {
		private String name;
		private boolean resizable;

		public JInternalFrameFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JInternalFrameFactory resizable(boolean shouldBeResizable) {
			resizable = shouldBeResizable;
			return this;
		}

		@RunsInEDT
		public JInternalFrame createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JInternalFrame executeInEDT() {
					JInternalFrame internalFrame = new JInternalFrame();
					internalFrame.setName(name);
					internalFrame.setResizable(resizable);
					return internalFrame;
				}
			});
		}
	}
}