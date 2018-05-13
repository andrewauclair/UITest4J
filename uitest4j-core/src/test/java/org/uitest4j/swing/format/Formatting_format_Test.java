/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.format;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

import static java.awt.Adjustable.VERTICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.builder.JButtons.button;
import static org.uitest4j.swing.test.builder.JDialogs.dialog;
import static org.uitest4j.swing.test.builder.JFrames.frame;
import static org.uitest4j.swing.test.builder.JLabels.label;
import static org.uitest4j.swing.test.builder.JMenuBars.menuBar;
import static org.uitest4j.swing.test.builder.JMenuItems.menuItem;
import static org.uitest4j.swing.test.builder.JPanels.panel;
import static org.uitest4j.swing.test.builder.JPopupMenus.popupMenu;
import static org.uitest4j.swing.test.builder.JProgressBars.progressBar;
import static org.uitest4j.swing.test.builder.JScrollBars.scrollBar;
import static org.uitest4j.swing.test.builder.JScrollPanes.scrollPane;
import static org.uitest4j.swing.test.builder.JSliders.slider;
import static org.uitest4j.swing.test.builder.JSpinners.spinner;
import static org.uitest4j.swing.test.builder.JTextFields.textField;
import static org.uitest4j.swing.test.builder.JToggleButtons.toggleButton;

/**
 * Tests for {@link Formatting#format(Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class Formatting_format_Test extends EDTSafeTestCase {
	private static Logger logger = Logger.getAnonymousLogger();

	@Test
	void should_Format_Dialog() {
		JDialog dialog = dialog().withName("dialog").withTitle("A dialog").createNew();
		assertThat(formatted(dialog)).contains("javax.swing.JDialog").contains("name='dialog'")
				.contains("title='A dialog'").contains("enabled='true'").contains("modal='false'")
				.contains("visible='false'")
				.contains("showing=false");
	}

	@Test
	void should_Format_Frame() {
		JFrame frame = frame().withName("frame").withTitle("A frame").createNew();
		assertThat(formatted(frame)).contains("javax.swing.JFrame").contains("name='frame'").contains("title='A frame'")
				.contains("enabled='true'").contains("visible='false'").contains("showing=false");
	}

	@Test
	void should_Format_JButton() {
		JButton button = button().enabled(false).withName("button").withText("A button").createNew();
		assertThat(formatted(button)).contains("javax.swing.JButton").contains("name='button'").contains("text='A button'")
				.contains("enabled='false'").contains("visible='true'").contains("showing=false");
	}

	@Test
	void should_Format_JComboBox() {
		assertThat(Formatting.formatter(JComboBox.class)).isInstanceOf(JComboBoxFormatter.class);
	}

	@Test
	void should_Format_JFileChooser() {
		assertThat(Formatting.formatter(JFileChooser.class)).isInstanceOf(JFileChooserFormatter.class);
	}

	@Test
	void should_Format_JLabel() {
		JLabel label = label().withName("label").withText("A label").createNew();
		assertThat(formatted(label)).contains("javax.swing.JLabel").contains("name='label'").contains("text='A label'")
				.contains("enabled='true'").contains("visible='true'").contains("showing=false");
	}

	@Test
	void should_Format_JLayeredPane() {
		JLayeredPane pane = newJLayeredPane();
		assertThat(formatted(pane)).isEqualTo("javax.swing.JLayeredPane[]");
	}

	private static JLayeredPane newJLayeredPane() {
		return new JLayeredPane();
	}

	@Test
	void should_Format_JList() {
		assertThat(Formatting.formatter(JList.class)).isInstanceOf(JListFormatter.class);
	}

	@Test
	void should_Format_JMenuBar() {
		JMenuBar menuBar = menuBar().createNew();
		assertThat(formatted(menuBar)).isEqualTo("javax.swing.JMenuBar[]");
	}

	@Test
	void should_Format_JMenuItem() {
		JMenuItem menuItem = menuItem().withName("menuItem").selected(true).withText("A menu item").createNew();
		assertThat(formatted(menuItem)).contains("javax.swing.JMenuItem").contains("name='menuItem'")
				.contains("text='A menu item'").contains("selected='true'").contains("enabled='true'")
				.contains("visible='true'")
				.contains("showing=false");
	}

	@Test
	void should_Format_JOptionPane() {
		assertThat(Formatting.formatter(JOptionPane.class)).isInstanceOf(JOptionPaneFormatter.class);
	}

	@Test
	void should_Format_JPanel() {
		JPanel panel = panel().withName("panel").createNew();
		assertThat(formatted(panel)).contains("javax.swing.JPanel").contains("name='panel'");
	}

	@Test
	void should_Format_JPasswordField() {
		JPasswordField passwordField = newJPasswordField();
		assertThat(formatted(passwordField)).contains("javax.swing.JPasswordField").contains("name='passwordField'")
				.contains("enabled='true'").contains("visible='true'").contains("showing=false");
	}

	@RunsInEDT
	private static JPasswordField newJPasswordField() {
		return execute(() -> {
			JPasswordField passwordField = new JPasswordField();
			passwordField.setName("passwordField");
			return passwordField;
		});
	}

	@Test
	void should_Format_JPopupMenu() {
		JPopupMenu popupMenu = popupMenu().withLabel("Menu").withName("popupMenu").createNew();
		assertThat(formatted(popupMenu)).contains("javax.swing.JPopupMenu").contains("name='popupMenu'")
				.contains("label='Menu'").contains("enabled='true'").contains("visible='false'")
				.contains("showing=false");
	}

	@Test
	void should_Format_JProgressBar() {
		JProgressBar progressBar = progressBar().showingText().withMaximum(20).withMinimum(10).withName("progressBar")
				.withText("15%").withValue(15).createNew();
		assertThat(formatted(progressBar)).contains("javax.swing.JProgressBar").contains("name='progressBar'")
				.contains("value='15'").contains("minimum='10'").contains("maximum='20'")
				.contains("string='15%'")
				.contains("stringPainted='true'").contains("enabled='true'").contains("visible='true'")
				.contains("showing=false");
	}

	@Test
	void should_Format_JRootPane() {
		JRootPane pane = newJRootPane();
		assertThat(formatted(pane)).isEqualTo("javax.swing.JRootPane[]");
	}

	@RunsInEDT
	private static JRootPane newJRootPane() {
		return execute(JRootPane::new);
	}

	@Test
	void should_Format_JScrollBar() {
		JScrollBar scrollBar = scrollBar().withBlockIncrement(10).withMinimum(0).withMaximum(60).withName("scrollBar")
				.withOrientation(VERTICAL).withValue(20).createNew();
		assertThat(formatted(scrollBar)).contains("javax.swing.JScrollBar").contains("name='scrollBar'")
				.contains("value='20'").contains("blockIncrement='10'").contains("minimum='0'")
				.contains("maximum='60'")
				.contains("enabled='true'").contains("visible='true'").contains("showing=false");
	}

	@Test
	void should_Format_JScrollPane() {
		JScrollPane scrollPane = scrollPane().withName("scrollPane").createNew();
		assertThat(formatted(scrollPane)).contains("javax.swing.JScrollPane").contains("name='scrollPane'")
				.contains("enabled='true'").contains("visible='true'").contains("showing=false");
	}

	@Test
	void should_Format_JSlider() {
		JSlider slider = slider().withMaximum(8).withMinimum(2).withValue(6).withName("slider").createNew();
		assertThat(formatted(slider)).contains("javax.swing.JSlider").contains("name='slider'").contains("value='6'")
				.contains("minimum='2'").contains("maximum='8'").contains("enabled='true'")
				.contains("visible='true'")
				.contains("showing=false");
	}

	@Test
	void should_Format_JSpinner() {
		JSpinner spinner = spinner().withName("spinner").withValues(6, 2, 8, 1).createNew();
		assertThat(formatted(spinner)).contains("javax.swing.JSpinner").contains("name='spinner'").contains("value='6'")
				.contains("enabled='true'").contains("visible='true'").contains("showing=false");
	}

	@Test
	void should_Format_JTabbedPane() {
		assertThat(Formatting.formatter(JTabbedPane.class)).isInstanceOf(JTabbedPaneFormatter.class);
	}

	@Test
	void should_Format_JTable() {
		assertThat(Formatting.formatter(JTable.class)).isInstanceOf(JTableFormatter.class);
	}

	@Test
	void should_Format_JTextComponent() {
		JTextField textField = textField().withName("textField").withText("Hello").createNew();
		assertThat(formatted(textField)).contains("javax.swing.JTextField").contains("name='textField'")
				.contains("text='Hello'").contains("enabled='true'").contains("visible='true'")
				.contains("showing=false");
	}

	@Test
	void should_Format_JToggleButton() {
		JToggleButton toggleButton = toggleButton().withName("toggleButton").selected(true).withText("Button").createNew();
		assertThat(formatted(toggleButton)).contains("javax.swing.JToggleButton").contains("name='toggleButton'")
				.contains("text='Button'").contains("selected='true'").contains("enabled='true'")
				.contains("visible='true'")
				.contains("showing=false");
	}

	@Test
	void should_Format_JTree() {
		assertThat(Formatting.formatter(JTree.class)).isInstanceOf(JTreeFormatter.class);
	}

	private String formatted(Component c) {
		String formatted = Formatting.format(c);
		logger.info(concat("formatted: ", formatted));
		return formatted;
	}

	@Test
	void should_Return_Message_Indicating_Component_Is_Null() {
		assertThat(Formatting.format(null)).isEqualTo("Null Component");
	}
}
