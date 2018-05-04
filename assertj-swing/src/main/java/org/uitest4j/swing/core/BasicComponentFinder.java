/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.opentest4j.AssertionFailedError;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.hierarchy.ComponentHierarchy;
import org.uitest4j.swing.hierarchy.ExistingHierarchy;
import org.uitest4j.swing.hierarchy.SingleComponentHierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Objects;

import static java.lang.System.lineSeparator;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.format.Formatting.format;
import static org.uitest4j.swing.hierarchy.NewHierarchy.ignoreExistingComponents;

/**
 * Default implementation of {@link ComponentFinder}.
 *
 * @author Alex Ruiz
 * @see ComponentFinder
 */
public final class BasicComponentFinder implements ComponentFinder {
	private final ComponentHierarchy hierarchy;
	private final ComponentPrinter printer;
	private final Settings settings;

	private final FinderDelegate finderDelegate = new FinderDelegate();

	private boolean includeHierarchyInComponentLookupException;

	/**
	 * Creates a new {@link BasicComponentFinder} with a new AWT hierarchy. AWT and Swing {@code Component}s created
	 * before the created {@link BasicComponentFinder} cannot be accessed by the created {@link BasicComponentFinder}.
	 *
	 * @return the created finder.
	 */
	@Nonnull
	public static ComponentFinder finderWithNewAwtHierarchy() {
		return new BasicComponentFinder(ignoreExistingComponents());
	}

	/**
	 * Creates a new {@link BasicComponentFinder} that has access to all the AWT and Swing {@code Component}s in the AWT
	 * hierarchy.
	 *
	 * @return the created finder.
	 */
	@Nonnull
	public static ComponentFinder finderWithCurrentAwtHierarchy() {
		return new BasicComponentFinder(new ExistingHierarchy());
	}

	/**
	 * Creates a new {@link BasicComponentFinder}. The created finder does not use any {@link Settings}.
	 *
	 * @param hierarchy the component hierarchy to use.
	 */
	protected BasicComponentFinder(@Nonnull ComponentHierarchy hierarchy) {
		this(hierarchy, null);
	}

	/**
	 * Creates a new {@link BasicComponentFinder}.
	 *
	 * @param hierarchy the component hierarchy to use.
	 * @param settings  the configuration settings to use. It can be {@code null}.
	 */
	protected BasicComponentFinder(@Nonnull ComponentHierarchy hierarchy, @Nullable Settings settings) {
		this.hierarchy = hierarchy;
		this.settings = settings;
		printer = new BasicComponentPrinter(hierarchy);
		includeHierarchyIfComponentNotFound(true);
	}

	@Override
	@Nonnull
	public ComponentPrinter printer() {
		return printer;
	}

	@Override
	@Nonnull
	public <T extends Component> T findByType(@Nonnull Class<T> type) {
		return findByType(type, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByType(@Nonnull Class<T> type, boolean showing) {
		return type.cast(find(new TypeMatcher(type, showing)));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByType(@Nonnull Container root, @Nonnull Class<T> type) {
		return findByType(root, type, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByType(@Nonnull Container root, @Nonnull Class<T> type, boolean showing) {
		return type.cast(find(root, new TypeMatcher(type, showing)));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByName(@Nullable String name, @Nonnull Class<T> type) {
		return findByName(name, type, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByName(@Nullable String name, @Nonnull Class<T> type, boolean showing) {
		Component found = find(new NameMatcher(name, type, showing));
		return type.cast(found);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByName(@Nullable String name) {
		return findByName(name, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByName(@Nullable String name, boolean showing) {
		return find(new NameMatcher(name, showing));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByLabel(@Nullable String label, @Nonnull Class<T> type) {
		return findByLabel(label, type, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByLabel(@Nullable String label, @Nonnull Class<T> type, boolean showing) {
		Component found = find(new LabelMatcher(label, type, showing));
		return labelFor(found, type);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByLabel(@Nullable String label) {
		return findByLabel(label, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByLabel(@Nullable String label, boolean showing) {
		Component found = find(new LabelMatcher(label, showing));
		return labelFor(found, Component.class);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T find(@Nonnull GenericTypeMatcher<T> m) {
		Component found = find((ComponentMatcher) m);
		return m.supportedType().cast(found);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component find(@Nonnull ComponentMatcher m) {
		return find(hierarchy, m);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByName(@Nonnull Container root, @Nullable String name,
											  @Nonnull Class<T> type) {
		return findByName(root, name, type, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByName(@Nonnull Container root, @Nullable String name,
											  @Nonnull Class<T> type, boolean showing) {
		Component found = find(root, new NameMatcher(name, type, showing));
		return type.cast(found);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByName(@Nonnull Container root, @Nullable String name) {
		return findByName(root, name, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByName(@Nonnull Container root, @Nullable String name, boolean showing) {
		return find(root, new NameMatcher(name, showing));
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByLabel(@Nonnull Container root, @Nullable String label,
											   @Nonnull Class<T> type) {
		return findByLabel(root, label, type, requireShowing());
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T findByLabel(@Nonnull Container root, @Nullable String label,
											   @Nonnull Class<T> type, boolean showing) {
		Component found = find(root, new LabelMatcher(label, type, showing));
		return labelFor(found, type);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByLabel(@Nonnull Container root, @Nullable String label) {
		return findByLabel(root, label, requireShowing());
	}

	private boolean requireShowing() {
		return requireShowingFromSettingsOr(false);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component findByLabel(@Nonnull Container root, @Nullable String label, boolean showing) {
		Component found = find(root, new LabelMatcher(label, showing));
		return labelFor(found, Component.class);
	}

	@Nonnull
	private <T> T labelFor(@Nonnull Component label, @Nonnull Class<T> type) {
		if (!(label instanceof JLabel)) {
			throw new IllegalArgumentException("label should be an instanceof of JLabel");
		}
		Component target = ((JLabel) label).getLabelFor();
		if (!target.getClass().isInstance(type)) {
			throw new AssertionFailedError("Expected getLabelFor of '" + label.getName() + "' to return a JLabel but was " + target.getClass().getSimpleName());
		}
		return type.cast(target);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public <T extends Component> T find(@Nonnull Container root, @Nonnull GenericTypeMatcher<T> m) {
		Component found = find(root, (ComponentMatcher) m);
		return m.supportedType().cast(found);
	}

	@RunsInEDT
	@Override
	@Nonnull
	public Component find(@Nullable Container root, @Nonnull ComponentMatcher m) {
		return find(hierarchy(root), m);
	}

	@RunsInEDT
	@Nonnull
	private Component find(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
		Collection<Component> found = finderDelegate.find(h, m);
		if (found.isEmpty()) {
			throw componentNotFound(h, m);
		}
		if (found.size() > 1) {
			throw multipleComponentsFound(found, m);
		}
		return Objects.requireNonNull(found.iterator().next());
	}

	@RunsInEDT
	@Nonnull
	private ComponentLookupException componentNotFound(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
		String message = "Unable to find component using matcher " + m + ".";
		if (includeHierarchyIfComponentNotFound()) {
			message = message + lineSeparator() + lineSeparator() + "Component hierarchy:" + lineSeparator() +
					formattedHierarchy(root(h));
		}
		throw new ComponentLookupException(message);
	}

	@Nullable
	private static Container root(@Nullable ComponentHierarchy h) {
		if (h instanceof SingleComponentHierarchy) {
			return ((SingleComponentHierarchy) h).root();
		}
		return null;
	}

	@RunsInEDT
	@Nonnull
	private String formattedHierarchy(@Nullable Container root) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(out, true);
		printer.printComponents(printStream, root);
		printStream.flush();
		return new String(out.toByteArray());
	}

	@RunsInEDT
	@Nonnull
	private static ComponentLookupException multipleComponentsFound(@Nonnull Collection<Component> found,
																	@Nonnull ComponentMatcher m) {
		StringBuilder message = new StringBuilder();
		String format = "Found more than one component using matcher %s. %n%nFound:";
		message.append(String.format(format, m.toString()));
		appendComponents(message, found);
		if (!found.isEmpty()) {
			message.append(lineSeparator());
		}
		throw new ComponentLookupException(message.toString(), found);
	}

	@RunsInEDT
	private static void appendComponents(final @Nonnull StringBuilder message, final @Nonnull Collection<Component> found) {
		execute(() -> {
			for (Component c : found) {
				message.append(String.format("%n%s", format(c)));
			}
		});
	}

	@Override
	public boolean includeHierarchyIfComponentNotFound() {
		return includeHierarchyInComponentLookupException;
	}

	@Override
	public void includeHierarchyIfComponentNotFound(boolean newValue) {
		includeHierarchyInComponentLookupException = newValue;
	}

	@Override
	@Nonnull
	public Collection<Component> findAll(@Nonnull ComponentMatcher m) {
		return finderDelegate.find(hierarchy, m);
	}

	@Override
	@Nonnull
	public Collection<Component> findAll(@Nonnull Container root, @Nonnull ComponentMatcher m) {
		return finderDelegate.find(hierarchy(root), m);
	}

	@Override
	@Nonnull
	public <T extends Component> Collection<T> findAll(@Nonnull GenericTypeMatcher<T> m) {
		return finderDelegate.find(hierarchy, m);
	}

	@Override
	@Nonnull
	public <T extends Component> Collection<T> findAll(@Nonnull Container root, @Nonnull GenericTypeMatcher<T> m) {
		ComponentHierarchy h = hierarchy(root);
		return finderDelegate.find(h, m);
	}

	/**
	 * Returns the value of the flag "requireShowing" in the {@link ComponentLookupScope} this finder's {@link Settings}.
	 * If the settings object is {@code null}, this method will return the provided default value.
	 *
	 * @param defaultValue the value to return if this matcher does not have any configuration settings.
	 * @return the value of the flag "requireShowing" in this finder's settings, or the provided default value if this
	 * finder does not have configuration settings.
	 */
	protected final boolean requireShowingFromSettingsOr(boolean defaultValue) {
		if (settings == null) {
			return defaultValue;
		}
		return settings.componentLookupScope().requireShowing();
	}

	@Nonnull
	private ComponentHierarchy hierarchy(@Nullable Container root) {
		if (root == null) {
			return hierarchy;
		}
		return new SingleComponentHierarchy(root, hierarchy);
	}
}
