/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.data.TableCell;
import org.uitest4j.swing.edt.GuiActionRunner;
import org.uitest4j.swing.fixture.FrameFixture;
import org.uitest4j.swing.test.core.SwingRobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestTable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Test for <a href="https://github.com/joel-costigliola/assertj-swing/issues/169">github.com - assertj-swing #169</a>
 *
 * @author Angelo Gülle
 */
public class GitHub_169_DisabledComponentTest extends SwingRobotBasedTestCase {
	protected FrameFixture window;

	@Override
	protected void onSetUp() {
		DisabledTableFrame mainFrame = GuiActionRunner.execute(DisabledTableFrame::new);

		window = new FrameFixture(robot, mainFrame);
	}

	@Test
	void openPopupMenu() {
		window.table().showPopupMenuAt(TableCell.row(0).column(0));
	}

	class DisabledTableFrame extends JFrame {

		private static final long serialVersionUID = 1L;

		DisabledTableFrame() {
			setSize(200, 100);
			setTitle(getClass().getCanonicalName());
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JTable table = new TestTable(1, 1);
			add(table);

			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.isPopupTrigger()) {
						showPopup();
					}
				}

				@SuppressWarnings("unused")
				private void showPopup() {
					new PopupMenu();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger()) {
						showPopup();
					}
				}

			});

			table.setEnabled(false);
			setVisible(true);
		}
	}

	class PopupMenu extends JPopupMenu {

		private static final long serialVersionUID = 1L;

		PopupMenu() {

			JMenuItem menuItem = new JMenuItem("TEST");
			add(menuItem);
			setVisible(true);
		}
	}
}