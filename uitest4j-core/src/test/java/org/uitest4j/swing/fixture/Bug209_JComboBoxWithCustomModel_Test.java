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
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.util.Arrays.array;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=209" target="_blank">Bug 209</a>.
 * <p>
 * Demonstrate bug when testing {@code JComboBox}es. If a custom model is used the {@code JComboBox} must be click
 * before {@code JComboBoxDriver} can find the pop-up list for it. (FEST 1.0b1, Java 1.5)
 * </p>
 *
 * @author Ewan McDougall
 * @author Alex Ruiz
 */
public class Bug209_JComboBoxWithCustomModel_Test extends RobotBasedTestCase {
	private DialogFixture dialog;

	@Override
	protected void onSetUp() {
		NamedObject[] values = array(new NamedObject("hat"), new NamedObject("son"));
		dialog = new DialogFixture(robot, MyDialog.createNew(values));
		dialog.show();
	}

	@Test
	void should_Have_First_Item_Selected() {
		dialog.comboBox("cb").requireSelection("hat");
	}

	@Test
	void should_Have_First_Item_Selected_After_Click() {
		dialog.comboBox("cb").click();
		dialog.comboBox("cb").requireSelection("hat");
	}

	private static class MyDialog extends JDialog {
		@RunsInEDT
		static MyDialog createNew(final NamedObject[] items) {
			return execute(() -> new MyDialog(items));
		}

		private MyDialog(NamedObject[] items) {
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(items));
			comboBox.setRenderer(new NamedObjectCellRenderer());
			comboBox.setSelectedIndex(0);
			comboBox.setName("cb");
			add(comboBox);
			setTitle(Bug209_JComboBoxWithCustomModel_Test.class.getSimpleName());
		}
	}

	private static class NamedObjectCellRenderer extends DefaultListCellRenderer {
		NamedObjectCellRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
													  boolean cellHasFocus) {
			String v = value.toString();
			if (value instanceof NamedObject) {
				v = ((NamedObject) value).name();
			}
			return super.getListCellRendererComponent(list, v, index, isSelected, cellHasFocus);
		}
	}

	private static class NamedObject {
		private final String name;

		NamedObject(String name) {
			this.name = name;
		}

		String name() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
