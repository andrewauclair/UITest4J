/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.finder.JOptionPaneFinder;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=76">Bug 76</a>.
 *
 * @author Wim Deblauwe
 * @author Yvonne Wang
 */
public class Bug76_JOptionPaneLookup_Test extends RobotBasedTestCase {
	private DialogFixture starter;

	@Test
	public void should_Find_JOptionPane() {
		JOptionPaneStarter optionPaneStarter = JOptionPaneStarter.createNew("Message 1");
		starter = new DialogFixture(robot, optionPaneStarter);
		starter.show();
		starter.requireVisible();
		starter.button().click();
		JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(robot);
		fixture.requireMessage("Message 1");
		fixture.button().click();
	}

	@Test
	public void should_Find_JOptionPane_Again() {
		JOptionPaneStarter optionPaneStarter = JOptionPaneStarter.createNew("Message 2");
		starter = new DialogFixture(robot, optionPaneStarter);
		starter.show();
		starter.requireVisible();
		starter.button().click();
		JOptionPaneFixture fixture = JOptionPaneFinder.findOptionPane().using(robot);
		fixture.requireMessage("Message 2");
		fixture.button().click();
	}

	private static class JOptionPaneStarter extends JDialog {
		@RunsInEDT
		static JOptionPaneStarter createNew(final String message) {
			return execute(() -> new JOptionPaneStarter(message));
		}

		private JOptionPaneStarter(String message) {
			super((Frame) null, "JOptionPane Starter");
			setContentPane(createContentPane(message));
		}

		private Container createContentPane(String message) {
			JPanel panel = new JPanel();
			panel.add(new JButton(new OpenJOptionPaneAction(message)));
			return panel;
		}

		private class OpenJOptionPaneAction extends AbstractAction {
			private final String m_message;

			OpenJOptionPaneAction(String message) {
				super("Start!");
				m_message = message;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(JOptionPaneStarter.this, m_message);
			}
		}
	}
}
