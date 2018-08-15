package org.uitest4j.examples.swing;

import javax.swing.*;

/**
 * @author Andrew Auclair
 */
public class ExampleFrame extends JFrame {
	public ExampleFrame() {
		JToolBar toolbar = new JToolBar();
		toolbar.setName("TestToolbar");
		
		JPanel lastPanel = null;
		for (int j = 0; j < 100; j++) {
			JPanel outerPanel = new JPanel();
			for (int i = 0; i < 100; i++) {
				JPanel panel = new JPanel();
				outerPanel.add(panel);
			}
			
			if (lastPanel != null) {
				lastPanel.add(outerPanel);
			}
			else {
				add(outerPanel);
			}
			lastPanel = outerPanel;
			
			if (j == 80) {
				outerPanel.add(toolbar);
			}
		}
		JPanel panel = new JPanel();
		
		panel.add(toolbar);
		add(panel);
		setVisible(true);
	}
}
