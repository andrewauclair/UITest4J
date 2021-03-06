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

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * {@code JFrame} that launches another {@code JFrame} and a {@link JDialog} using a configurable delay.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WindowLauncher extends TestWindow {
	private static final int DEFAULT_DELAY = 10000;

	private int windowLaunchDelay = DEFAULT_DELAY;
	private int dialogLaunchDelay = DEFAULT_DELAY;

	@RunsInEDT
	public static WindowLauncher createNew(final Class<?> testClass) {
		return execute(() -> new WindowLauncher(testClass));
	}

	private WindowLauncher(Class<?> testClass) {
		super(testClass);
		add(windowLaunchButton());
		add(dialogLaunchButton());
	}

	@RunsInCurrentThread
	private JButton windowLaunchButton() {
		JButton button = new JButton("Launch Frame");
		button.setName("launchFrame");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				launchWindow();
			}
		});
		return button;
	}

	@RunsInCurrentThread
	void launchWindow() {
		start(new Timer(windowLaunchDelay, e -> showWindow(new WindowToLaunch())));
	}

	@RunsInCurrentThread
	private JButton dialogLaunchButton() {
		JButton button = new JButton("Launch Dialog");
		button.setName("launchDialog");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				launchDialog();
			}
		});
		return button;
	}

	@RunsInCurrentThread
	private void launchDialog() {
		if (dialogLaunchDelay == 0) {
			showWindow(new DialogToLaunch());
			return;
		}
		start(new Timer(dialogLaunchDelay, e -> showWindow(new DialogToLaunch())));
	}

	@RunsInCurrentThread
	private void start(Timer timer) {
		timer.setRepeats(false);
		timer.start();
	}

	@RunsInCurrentThread
	private void showWindow(Window window) {
		window.pack();
		window.setVisible(true);
	}

	public void windowLaunchDelay(int newWindowLaunchDelay) {
		windowLaunchDelay = newWindowLaunchDelay;
	}

	public void dialogLaunchDelay(int newDialogLaunchDelay) {
		dialogLaunchDelay = newDialogLaunchDelay;
	}

	public static class WindowToLaunch extends JFrame {
		public WindowToLaunch() {
			setName("frame");
			setTitle("Launched Window");
			setPreferredSize(new Dimension(100, 50));
		}
	}

	public static class DialogToLaunch extends JDialog {
		public DialogToLaunch() {
			setName("dialog");
			setTitle("Launched Dialog");
			setPreferredSize(new Dimension(100, 50));
		}
	}
}
