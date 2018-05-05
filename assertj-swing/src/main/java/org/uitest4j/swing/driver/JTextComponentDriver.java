/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.exception.ActionFailedException;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.util.Platform;
import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.valueOf;
import static javax.swing.text.DefaultEditorKit.deletePrevCharAction;
import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JTextComponentEditableQuery.isEditable;
import static org.uitest4j.swing.driver.JTextComponentSelectAllTask.selectAllText;
import static org.uitest4j.swing.driver.JTextComponentSelectTextTask.selectTextInRange;
import static org.uitest4j.swing.driver.JTextComponentSetTextTask.setTextIn;
import static org.uitest4j.swing.driver.PointAndParentForScrollingJTextFieldQuery.pointAndParentForScrolling;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.exception.ActionFailedException.actionFailure;
import static org.uitest4j.swing.format.Formatting.format;

/**
 * <p>
 * Supports functional testing of {@code JTextComponent}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 */
@InternalApi
public class JTextComponentDriver extends JComponentDriver implements TextDisplayDriver<JTextComponent> {
	/**
	 * Creates a new {@link JTextComponentDriver}.
	 *
	 * @param robot the robot to use to simulate user input.
	 */
	public JTextComponentDriver(@Nonnull Robot robot) {
		super(robot);
	}

	/**
	 * Deletes the text of the {@code JTextComponent}.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @throws IllegalStateException if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
	 */
	@RunsInEDT
	public void deleteText(@Nonnull JTextComponent textBox) {
		selectAll(textBox);
		invokeAction(textBox, deletePrevCharAction);
	}

	/**
	 * Types the given text into the {@code JTextComponent}, replacing any existing text already there.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @param text    the text to enter. Uses {@link #deleteText(JTextComponent)} if this is an empty string.
	 * @throws NullPointerException     if the text to enter is {@code null}.
	 * @throws IllegalArgumentException if the text to enter is empty.
	 * @throws IllegalStateException    if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException    if the {@code JTextComponent} is not showing on the screen.
	 */
	@RunsInEDT
	public void replaceText(@Nonnull JTextComponent textBox, @Nonnull String text) {
		Objects.requireNonNull(text);
		if (text.isEmpty()) {
			deleteText(textBox);
		}
		else {
			selectAll(textBox);
			enterText(textBox, text);
		}
	}

	/**
	 * Selects the text in the {@code JTextComponent}.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @throws IllegalStateException if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
	 */
	@RunsInEDT
	public void selectAll(@Nonnull JTextComponent textBox) {
		checkStateAndScrollToPosition(textBox, 0);
		selectAllText(textBox);
	}

	/**
	 * Types the given text into the {@code JTextComponent}.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @param text    the text to enter.
	 * @throws IllegalStateException if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
	 */
	@RunsInEDT
	public void enterText(@Nonnull JTextComponent textBox, @Nonnull String text) {
		focusAndWaitForFocusGain(textBox);
		robot.enterText(text);
	}

	/**
	 * <p>
	 * Sets the given text into the {@code JTextComponent}. Unlike {@link #enterText(JTextComponent, String)}, this method
	 * bypasses the event system and allows immediate updating on the underlying document model.
	 * </p>
	 * <p>
	 * Primarily desired for speeding up tests when precise user event fidelity isn't necessary.
	 * </p>
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @param text    the text to enter.
	 * @throws IllegalStateException if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
	 */
	@RunsInEDT
	public void setText(@Nonnull JTextComponent textBox, @Nullable String text) {
		focusAndWaitForFocusGain(textBox);
		setTextIn(textBox, text);
		robot.waitForIdle();
	}

	/**
	 * Select the given text range.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @param text    the text to select.
	 * @throws IllegalStateException    if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException    if the {@code JTextComponent} is not showing on the screen.
	 * @throws IllegalArgumentException if the {@code JTextComponent} does not contain the given text to select.
	 * @throws ActionFailedException    if selecting the text fails.
	 */
	@RunsInEDT
	public void selectText(@Nonnull JTextComponent textBox, @Nonnull String text) {
		int indexFound = indexOfText(textBox, text);
		if (indexFound == -1) {
			throw new IllegalArgumentException(String.format("The text '%s' was not found", text));
		}
		selectText(textBox, indexFound, indexFound + text.length());
	}

	@RunsInEDT
	private static int indexOfText(final @Nonnull JTextComponent textBox, final @Nonnull String text) {
		Integer result = execute(() -> {
			checkEnabledAndShowing(textBox);
			String actualText = textBox.getText();
			if (Strings.isNullOrEmpty(actualText)) {
				return -1;
			}
			return actualText.indexOf(text);
		});
		return Objects.requireNonNull(result);
	}

	/**
	 * Select the given text range.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @param start   the starting index of the selection.
	 * @param end     the ending index of the selection.
	 * @throws IllegalStateException if the {@code JTextComponent} is disabled.
	 * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
	 * @throws ActionFailedException if selecting the text in the given range fails.
	 */
	@RunsInEDT
	public void selectText(@Nonnull JTextComponent textBox, int start, int end) {
		robot.moveMouse(textBox, checkStateAndScrollToPosition(textBox, start));
		robot.moveMouse(textBox, scrollToPosition(textBox, end));
		performAndValidateTextSelection(textBox, start, end);
	}

	@RunsInEDT
	@Nonnull
	private static Point checkStateAndScrollToPosition(final @Nonnull JTextComponent textBox, final int index) {
		Point result = execute(() -> {
			checkEnabledAndShowing(textBox);
			return scrollToVisible(textBox, index);
		});
		return Objects.requireNonNull(result);
	}

	@RunsInEDT
	@Nonnull
	private static Point scrollToPosition(final @Nonnull JTextComponent textBox, final int index) {
		Point result = execute(() -> scrollToVisible(textBox, index));
		return Objects.requireNonNull(result);
	}

	/**
	 * Move the pointer to the location of the given index. Takes care of auto-scrolling through text.
	 *
	 * @param textBox the target {@code JTextComponent}.
	 * @param index   the given location.
	 * @return the position of the pointer after being moved.
	 * @throws ActionFailedException if it was not possible to scroll to the location of the given index.
	 */
	@RunsInCurrentThread
	@Nonnull
	private static Point scrollToVisible(@Nonnull JTextComponent textBox, int index) {
		Rectangle indexLocation = locationOf(textBox, index);
		if (isRectangleVisible(textBox, indexLocation)) {
			return centerOf(indexLocation);
		}
		scrollToVisible(textBox, indexLocation);
		indexLocation = locationOf(textBox, index);
		if (isRectangleVisible(textBox, indexLocation)) {
			return centerOf(indexLocation);
		}
		String format = "Unable to make visible the location of the index <%d> by scrolling to the point <%s> on %s";
		String msg = String.format(format, index, formatOriginOf(indexLocation), format(textBox));
		throw actionFailure(msg);
	}

	@RunsInCurrentThread
	@Nonnull
	private static Rectangle locationOf(@Nonnull JTextComponent textBox, int index) {
		Rectangle r = null;
		try {
			r = textBox.modelToView(index);
		}
		catch (BadLocationException e) {
			throw cannotGetLocation(textBox, index);
		}
		if (r != null) {
			if (Platform.isMacintosh() && r.y == -1) {
				r.y = 0;
			}
			return r;
		}
		throw cannotGetLocation(textBox, index);
	}

	private static ActionFailedException cannotGetLocation(@Nonnull JTextComponent textBox, int index) {
		String msg = String.format("Unable to get location for index <%d> in %s", index, format(textBox));
		throw actionFailure(msg);
	}

	@RunsInCurrentThread
	private static boolean isRectangleVisible(@Nonnull JTextComponent textBox, @Nonnull Rectangle r) {
		Rectangle visible = textBox.getVisibleRect();
		return visible.contains(r.x, r.y);
	}

	private static String formatOriginOf(Rectangle r) {
		return valueOf(r.x) + "," + valueOf(r.y);
	}

	@RunsInCurrentThread
	private static void scrollToVisible(@Nonnull JTextComponent textBox, @Nonnull Rectangle r) {
		textBox.scrollRectToVisible(r);
		if (isVisible(textBox, r)) {
			return;
		}
		scrollToVisibleIfIsTextField(textBox, r);
	}

	@RunsInCurrentThread
	private static void scrollToVisibleIfIsTextField(@Nonnull JTextComponent textBox, @Nonnull Rectangle r) {
		if (!(textBox instanceof JTextField)) {
			return;
		}
		Pair<Point, Container> pointAndParent = pointAndParentForScrolling((JTextField) textBox);
		Container parent = pointAndParent.second;
		if (parent == null || parent instanceof CellRendererPane || !(parent instanceof JComponent)) {
			return;
		}
		((JComponent) parent).scrollRectToVisible(addPointToRectangle(Objects.requireNonNull(pointAndParent.first), r));
	}

	@Nonnull
	private static Rectangle addPointToRectangle(@Nonnull Point p, @Nonnull Rectangle r) {
		Rectangle destination = new Rectangle(r);
		destination.x += p.x;
		destination.y += p.y;
		return destination;
	}

	@Nonnull
	private static Point centerOf(@Nonnull Rectangle r) {
		return new Point(r.x + r.width / 2, r.y + r.height / 2);
	}

	@RunsInEDT
	private static void performAndValidateTextSelection(final @Nonnull JTextComponent textBox, final int start,
														final int end) {
		execute(() -> {
			selectTextInRange(textBox, start, end);
			verifyTextWasSelected(textBox, start, end);
		});
	}

	@RunsInCurrentThread
	private static void verifyTextWasSelected(@Nonnull JTextComponent textBox, int start, int end) {
		int actualStart = textBox.getSelectionStart();
		int actualEnd = textBox.getSelectionEnd();
		if (actualStart == min(start, end) && actualEnd == max(start, end)) {
			return;
		}
		String format = "Unable to select text uses indices <%d> and <%d>, current selection starts at <%d> and ends at <%d>";
		String msg = String.format(format, start, end, actualStart, actualEnd);
		throw actionFailure(msg);
	}

	/**
	 * Asserts that the text in the given {@code JTextComponent} is equal to the specified value.
	 *
	 * @param textBox  the given {@code JTextComponent}.
	 * @param expected the text to match. It can be a regular expression pattern.
	 * @throws AssertionError if the text of the {@code JTextComponent} is not equal to the given one.
	 */
	@RunsInEDT
	@Override
	public void requireText(@Nonnull JTextComponent textBox, @Nullable String expected) {
		OpenTest4JAssertions.assertEquals(expected, textOf(textBox), () -> "Expected text of '" + textBox.getName() +
				"' to be '" + expected + "' but was '" + textOf(textBox) + "'");
	}

	/**
	 * Asserts that the text in the given {@code JTextComponent} matches the given regular expression pattern.
	 *
	 * @param textBox the given {@code JTextComponent}.
	 * @param pattern the regular expression pattern to match.
	 * @throws NullPointerException if the given regular expression pattern is {@code null}.
	 * @throws AssertionError       if the text of the {@code JTextComponent} is not equal to the given one.
	 */
	@Override
	@RunsInEDT
	public void requireText(@Nonnull JTextComponent textBox, @Nonnull Pattern pattern) {
		OpenTest4JAssertions.assertMatchesPattern(pattern, textOf(textBox),
				() -> String.format("Expected text of '%s' to match pattern '%s' but was '%s'", textBox.getName(), pattern, textOf(textBox)));
	}

	/**
	 * Asserts that the given {@code JTextComponent} is empty.
	 *
	 * @param textBox the given {@code JTextComponent}.
	 * @throws AssertionError if the {@code JTextComponent} is not empty.
	 */
	@RunsInEDT
	public void requireEmpty(@Nonnull JTextComponent textBox) {
		OpenTest4JAssertions.assertEmpty(textOf(textBox),
				() -> String.format("Expected '%s' to be empty but was '%s'", textBox.getName(), textOf(textBox)));
	}

	/**
	 * Asserts that the given {@code JTextComponent} is editable.
	 *
	 * @param textBox the given {@code JTextComponent}.
	 * @throws AssertionError if the {@code JTextComponent} is not editable.
	 */
	@RunsInEDT
	public void requireEditable(@Nonnull JTextComponent textBox) {
		OpenTest4JAssertions.assertTrue(isEditable(textBox),
				() -> String.format("Expected '%s' to be editable", textBox.getName()));
	}

	/**
	 * Asserts that the given {@code JTextComponent} is not editable.
	 *
	 * @param textBox the given {@code JTextComponent}.
	 * @throws AssertionError if the {@code JTextComponent} is editable.
	 */
	@RunsInEDT
	public void requireNotEditable(@Nonnull JTextComponent textBox) {
		OpenTest4JAssertions.assertFalse(isEditable(textBox),
				() -> String.format("Expected '%s' to not be editable", textBox.getName()));
	}

	/**
	 * Returns the text of the given {@code JTextComponent}.
	 *
	 * @param textBox the given {@code JTextComponent}.
	 * @return the text of the given {@code JTextComponent}.
	 */
	@RunsInEDT
	@Override
	@Nullable
	public String textOf(@Nonnull JTextComponent textBox) {
		return JTextComponentTextQuery.textOf(textBox);
	}
}
