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

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.*;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.ComponentDriver;
import org.uitest4j.swing.timing.Timeout;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Objects;

import static org.uitest4j.swing.core.ComponentLookupScope.SHOWING_ONLY;
import static org.uitest4j.swing.timing.Pause.pause;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Looks up AWT or Swing {@code Component}s contained in a {@code Container}.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *            target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <C> the type of {@code Container} that this fixture can manage.
 * @param <D> the type of {@link ComponentDriver} that this fixture uses internally.
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractContainerFixture<S, C extends Container, D extends ComponentDriver> extends
		AbstractComponentFixture<S, C, D> implements ComponentContainerFixture {
	private static final Timeout DEFAULT_DIALOG_LOOKUP_TIMEOUT = timeout();

	private final JMenuItemFinder menuItemFinder;

	/**
	 * Creates a new {@link AbstractContainerFixture}.
	 *
	 * @param selfType the "self type."
	 * @param robot    performs simulation of user events on a {@code Container}.
	 * @param type     the type of the {@code Container} to find using the given {@code Robot}.
	 * @throws NullPointerException                                  if {@code robot} is {@code null}.
	 * @throws NullPointerException                                  if {@code type} is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching component could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching component is found.
	 * @see org.uitest4j.swing.core.ComponentFinder#findByType(Class)
	 */
	public AbstractContainerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull Class<? extends C> type) {
		super(selfType, robot, type);
		menuItemFinder = new JMenuItemFinder(robot, target());
	}

	/**
	 * Creates a new {@link AbstractContainerFixture}.
	 *
	 * @param selfType the "self type."
	 * @param robot    performs simulation of user events on a {@code Container}.
	 * @param name     the name of the {@code Container} to find using the given {@code Robot}.
	 * @param type     the type of the {@code Container} to find using the given {@code Robot}.
	 * @throws NullPointerException                                  if {@code robot} is {@code null}.
	 * @throws NullPointerException                                  if {@code type} is {@code null}.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching component could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching component is found.
	 * @see org.uitest4j.swing.core.ComponentFinder#findByName(String, Class)
	 */
	public AbstractContainerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String name,
									@Nonnull Class<? extends C> type) {
		super(selfType, robot, name, type);
		menuItemFinder = new JMenuItemFinder(robot, target());
	}

	/**
	 * Creates a new {@link AbstractContainerFixture}.
	 *
	 * @param selfType the "self type."
	 * @param robot    performs simulation of user events on the given {@code Container}.
	 * @param target   the {@code Container} to be.
	 * @throws NullPointerException if {@code robot} is {@code null}.
	 * @throws NullPointerException if {@code target} is {@code null}.
	 */
	public AbstractContainerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull C target) {
		super(selfType, robot, target);
		menuItemFinder = new JMenuItemFinder(robot, target());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JButtonFixture button() {
		return new JButtonFixture(robot(), findByType(JButton.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JButtonFixture button(@Nonnull GenericTypeMatcher<? extends JButton> matcher) {
		return new JButtonFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JButtonFixture button(@Nullable String name) {
		return new JButtonFixture(robot(), findByName(name, JButton.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JCheckBoxFixture checkBox() {
		return new JCheckBoxFixture(robot(), findByType(JCheckBox.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JCheckBoxFixture checkBox(@Nonnull GenericTypeMatcher<? extends JCheckBox> matcher) {
		return new JCheckBoxFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JCheckBoxFixture checkBox(@Nullable String name) {
		return new JCheckBoxFixture(robot(), findByName(name, JCheckBox.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JComboBoxFixture comboBox() {
		return new JComboBoxFixture(robot(), findByType(JComboBox.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JComboBoxFixture comboBox(@Nonnull GenericTypeMatcher<? extends JComboBox> matcher) {
		return new JComboBoxFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JComboBoxFixture comboBox(@Nullable String name) {
		return new JComboBoxFixture(robot(), findByName(name, JComboBox.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public DialogFixture dialog() {
		return dialog(defaultDialogLookupTimeout());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public DialogFixture dialog(@Nonnull Timeout timeout) {
		TypeMatcher matcher = new TypeMatcher(Dialog.class, requireShowing());
		return findDialog(matcher, timeout);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public DialogFixture dialog(@Nonnull GenericTypeMatcher<? extends Dialog> matcher) {
		return dialog(matcher, defaultDialogLookupTimeout());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public DialogFixture dialog(@Nonnull GenericTypeMatcher<? extends Dialog> matcher, @Nonnull Timeout timeout) {
		return findDialog(matcher, timeout);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public DialogFixture dialog(@Nullable String name) {
		return dialog(name, defaultDialogLookupTimeout());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public DialogFixture dialog(@Nullable String name, @Nonnull Timeout timeout) {
		NameMatcher matcher = new NameMatcher(name, Dialog.class, requireShowing());
		return findDialog(matcher, timeout);
	}

	@Nonnull
	private DialogFixture findDialog(@Nonnull ComponentMatcher matcher, @Nonnull Timeout timeout) {
		String description = "dialog to be found using matcher " + matcher;
		ComponentFoundCondition condition = new ComponentFoundCondition(description, robot().finder(), matcher);
		pause(condition, timeout);
		Dialog dialog = (Dialog) condition.found();
		return new DialogFixture(robot(), Objects.requireNonNull(dialog));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JFileChooserFixture fileChooser() {
		return fileChooser(defaultDialogLookupTimeout());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JFileChooserFixture fileChooser(@Nonnull Timeout timeout) {
		TypeMatcher matcher = new TypeMatcher(JFileChooser.class, requireShowing());
		return findFileChooser(matcher, timeout);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JFileChooserFixture fileChooser(@Nonnull GenericTypeMatcher<? extends JFileChooser> matcher) {
		return fileChooser(matcher, defaultDialogLookupTimeout());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JFileChooserFixture fileChooser(@Nonnull GenericTypeMatcher<? extends JFileChooser> matcher,
										   @Nonnull Timeout timeout) {
		return findFileChooser(matcher, timeout);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JFileChooserFixture fileChooser(@Nullable String name) {
		return new JFileChooserFixture(robot(), findByName(name, JFileChooser.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JFileChooserFixture fileChooser(@Nullable String name, @Nonnull Timeout timeout) {
		NameMatcher matcher = new NameMatcher(name, JFileChooser.class, requireShowing());
		return findFileChooser(matcher, timeout);
	}

	@Nonnull
	private JFileChooserFixture findFileChooser(@Nonnull ComponentMatcher matcher, @Nonnull Timeout timeout) {
		String description = "file chooser to be found using matcher " + matcher;
		ComponentFoundCondition condition = new ComponentFoundCondition(description, robot().finder(), matcher);
		pause(condition, timeout);
		JFileChooser fileChooser = (JFileChooser) condition.found();
		return new JFileChooserFixture(robot(), Objects.requireNonNull(fileChooser));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JInternalFrameFixture internalFrame() {
		return new JInternalFrameFixture(robot(), findByType(JInternalFrame.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JInternalFrameFixture internalFrame(@Nonnull GenericTypeMatcher<? extends JInternalFrame> matcher) {
		return new JInternalFrameFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JInternalFrameFixture internalFrame(@Nullable String name) {
		return new JInternalFrameFixture(robot(), findByName(name, JInternalFrame.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JLabelFixture label() {
		return new JLabelFixture(robot(), findByType(JLabel.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JLabelFixture label(@Nonnull GenericTypeMatcher<? extends JLabel> matcher) {
		return new JLabelFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JLabelFixture label(@Nullable String name) {
		return new JLabelFixture(robot(), findByName(name, JLabel.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JListFixture list() {
		return new JListFixture(robot(), findByType(JList.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JListFixture list(@Nonnull GenericTypeMatcher<? extends JList> matcher) {
		return new JListFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JListFixture list(@Nullable String name) {
		return new JListFixture(robot(), findByName(name, JList.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JMenuItemFixture menuItemWithPath(@Nonnull String... path) {
		return new JMenuItemFixture(robot(), menuItemFinder.menuItemWithPath(path));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JMenuItemFixture menuItem(@Nullable String name) {
		boolean requireShowing = SHOWING_ONLY.equals(robot().settings().componentLookupScope());
		return new JMenuItemFixture(robot(), finder().findByName(target(), name, JMenuItem.class, requireShowing));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JMenuItemFixture menuItem(@Nonnull GenericTypeMatcher<? extends JMenuItem> matcher) {
		return new JMenuItemFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JOptionPaneFixture optionPane() {
		return optionPane(defaultDialogLookupTimeout());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JOptionPaneFixture optionPane(@Nonnull Timeout timeout) {
		TypeMatcher matcher = new TypeMatcher(JOptionPane.class, requireShowing());
		String description = "option pane to be found using matcher " + matcher;
		ComponentFoundCondition condition = new ComponentFoundCondition(description, robot().finder(), matcher);
		pause(condition, timeout);
		JOptionPane optionPane = (JOptionPane) condition.found();
		return new JOptionPaneFixture(robot(), Objects.requireNonNull(optionPane));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JPanelFixture panel() {
		return new JPanelFixture(robot(), findByType(JPanel.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JPanelFixture panel(@Nonnull GenericTypeMatcher<? extends JPanel> matcher) {
		return new JPanelFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JPanelFixture panel(@Nullable String name) {
		return new JPanelFixture(robot(), findByName(name, JPanel.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JProgressBarFixture progressBar() {
		return new JProgressBarFixture(robot(), findByType(JProgressBar.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JProgressBarFixture progressBar(@Nonnull GenericTypeMatcher<? extends JProgressBar> matcher) {
		return new JProgressBarFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JProgressBarFixture progressBar(@Nullable String name) {
		return new JProgressBarFixture(robot(), findByName(name, JProgressBar.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JRadioButtonFixture radioButton() {
		return new JRadioButtonFixture(robot(), findByType(JRadioButton.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JRadioButtonFixture radioButton(@Nonnull GenericTypeMatcher<? extends JRadioButton> matcher) {
		return new JRadioButtonFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JRadioButtonFixture radioButton(@Nullable String name) {
		return new JRadioButtonFixture(robot(), findByName(name, JRadioButton.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JScrollBarFixture scrollBar() {
		return new JScrollBarFixture(robot(), findByType(JScrollBar.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JScrollBarFixture scrollBar(@Nonnull GenericTypeMatcher<? extends JScrollBar> matcher) {
		return new JScrollBarFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JScrollBarFixture scrollBar(@Nullable String name) {
		return new JScrollBarFixture(robot(), findByName(name, JScrollBar.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JScrollPaneFixture scrollPane() {
		return new JScrollPaneFixture(robot(), findByType(JScrollPane.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JScrollPaneFixture scrollPane(@Nonnull GenericTypeMatcher<? extends JScrollPane> matcher) {
		return new JScrollPaneFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JScrollPaneFixture scrollPane(@Nullable String name) {
		return new JScrollPaneFixture(robot(), findByName(name, JScrollPane.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSliderFixture slider() {
		return new JSliderFixture(robot(), findByType(JSlider.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSliderFixture slider(@Nonnull GenericTypeMatcher<? extends JSlider> matcher) {
		return new JSliderFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSliderFixture slider(@Nullable String name) {
		return new JSliderFixture(robot(), findByName(name, JSlider.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSpinnerFixture spinner() {
		return new JSpinnerFixture(robot(), findByType(JSpinner.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSpinnerFixture spinner(@Nonnull GenericTypeMatcher<? extends JSpinner> matcher) {
		return new JSpinnerFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSpinnerFixture spinner(@Nullable String name) {
		return new JSpinnerFixture(robot(), findByName(name, JSpinner.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSplitPaneFixture splitPane() {
		return new JSplitPaneFixture(robot(), findByType(JSplitPane.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSplitPaneFixture splitPane(@Nonnull GenericTypeMatcher<? extends JSplitPane> matcher) {
		return new JSplitPaneFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JSplitPaneFixture splitPane(@Nullable String name) {
		return new JSplitPaneFixture(robot(), findByName(name, JSplitPane.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTabbedPaneFixture tabbedPane() {
		return new JTabbedPaneFixture(robot(), findByType(JTabbedPane.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTabbedPaneFixture tabbedPane(@Nonnull GenericTypeMatcher<? extends JTabbedPane> matcher) {
		return new JTabbedPaneFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTabbedPaneFixture tabbedPane(@Nullable String name) {
		return new JTabbedPaneFixture(robot(), findByName(name, JTabbedPane.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTableFixture table() {
		return new JTableFixture(robot(), findByType(JTable.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTableFixture table(@Nonnull GenericTypeMatcher<? extends JTable> matcher) {
		return new JTableFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTableFixture table(@Nullable String name) {
		return new JTableFixture(robot(), findByName(name, JTable.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTextComponentFixture textBox() {
		return new JTextComponentFixture(robot(), findByType(JTextComponent.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTextComponentFixture textBox(@Nonnull GenericTypeMatcher<? extends JTextComponent> matcher) {
		return new JTextComponentFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTextComponentFixture textBox(@Nullable String name) {
		return new JTextComponentFixture(robot(), findByName(name, JTextComponent.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JToggleButtonFixture toggleButton() {
		return new JToggleButtonFixture(robot(), findByType(JToggleButton.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JToggleButtonFixture toggleButton(@Nonnull GenericTypeMatcher<? extends JToggleButton> matcher) {
		return new JToggleButtonFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JToggleButtonFixture toggleButton(@Nullable String name) {
		return new JToggleButtonFixture(robot(), findByName(name, JToggleButton.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JToolBarFixture toolBar() {
		return new JToolBarFixture(robot(), findByType(JToolBar.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JToolBarFixture toolBar(@Nonnull GenericTypeMatcher<? extends JToolBar> matcher) {
		return new JToolBarFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JToolBarFixture toolBar(@Nullable String name) {
		return new JToolBarFixture(robot(), findByName(name, JToolBar.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTreeFixture tree() {
		return new JTreeFixture(robot(), findByType(JTree.class));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTreeFixture tree(@Nonnull GenericTypeMatcher<? extends JTree> matcher) {
		return new JTreeFixture(robot(), find(matcher));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public JTreeFixture tree(@Nullable String name) {
		return new JTreeFixture(robot(), findByName(name, JTree.class));
	}

	/**
	 * Finds a component by type, contained in this fixture's {@code Container}.
	 *
	 * @param <T>  the type of component to find.
	 * @param type the class for the type.
	 * @return the found component.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching component could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching component is found.
	 */
	protected final @Nonnull
	<T extends Component> T findByType(@Nonnull Class<T> type) {
		return finder().findByType(target(), type, requireShowing());
	}

	/**
	 * Finds a component by name and type, contained in this fixture's {@code Container}.
	 *
	 * @param name the name of the component to find.
	 * @param <T>  the type of component to find.
	 * @param type the class for the type.
	 * @return the found component.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching component could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching component is found.
	 */
	protected final @Nonnull
	<T extends Component> T findByName(@Nullable String name, @Nonnull Class<T> type) {
		return finder().findByName(target(), name, type, requireShowing());
	}

	/**
	 * Finds a {@code Component} using the given {@link GenericTypeMatcher}, contained in this fixture's {@code Container}
	 * .
	 *
	 * @param <T>     the type of component to find.
	 * @param matcher the matcher to use to find the component.
	 * @return the found component.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if a matching component could not be found.
	 * @throws org.uitest4j.swing.exception.ComponentLookupException if more than one matching component is found.
	 */
	protected final @Nonnull
	<T extends Component> T find(@Nonnull GenericTypeMatcher<? extends T> matcher) {
		return finder().find(target(), matcher);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component, F extends AbstractComponentFixture<?, T, ?>> F with(
			@Nonnull ComponentFixtureExtension<T, F> extension) {
		return extension.createFixture(robot(), target());
	}

	/**
	 * @return the {@code ComponentFinder} contained in this fixture's {@code Robot}.
	 */
	protected final @Nonnull
	ComponentFinder finder() {
		return robot().finder();
	}

	/**
	 * @return the timeout to use when looking for a dialog. It's value is 100 ms.
	 */
	@Override
	@Nonnull
	public Timeout defaultDialogLookupTimeout() {
		return DEFAULT_DIALOG_LOOKUP_TIMEOUT;
	}
}
