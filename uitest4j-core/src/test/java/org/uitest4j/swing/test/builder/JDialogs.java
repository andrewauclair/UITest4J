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

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@link JDialog}s.
 *
 * @author Alex Ruiz
 */
public final class JDialogs {
	private JDialogs() {
	}

	public static JDialogFactory dialog() {
		return new JDialogFactory();
	}

	public static class JDialogFactory {
		private String name;
		private Frame owner;
		private boolean resizable = true;
		private String title;

		public JDialogFactory withOwner(Frame newOwner) {
			owner = newOwner;
			return this;
		}

		public JDialogFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JDialogFactory withTitle(String newTitle) {
			title = newTitle;
			return this;
		}

		public JDialogFactory resizable(boolean shouldBeResizable) {
			resizable = shouldBeResizable;
			return this;
		}

		@RunsInEDT
		public JDialog createNew() {
			return execute(this::create);
		}

		@RunsInEDT
		public JDialog createAndShow() {
			return execute(new GuiQuery<>() {
				@Override
				protected JDialog executeInEDT() {
					JDialog dialog = create();
					dialog.pack();
					dialog.setVisible(true);
					return dialog;
				}
			});
		}

		@RunsInCurrentThread
		JDialog create() {
			JDialog dialog = owner != null ? new JDialog(owner) : new JDialog();
			dialog.setName(name);
			dialog.setTitle(title);
			dialog.setResizable(resizable);
			return dialog;
		}
	}
}