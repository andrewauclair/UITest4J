/**
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
package org.uitest4j.swing.image;

import org.uitest4j.swing.internal.annotation.IORuntimeException;
import org.uitest4j.swing.util.RobotFactory;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static org.uitest4j.swing.core.FocusOwnerFinder.focusOwner;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.image.ImageFileExtensions.PNG;
import static org.uitest4j.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.uitest4j.swing.query.ComponentSizeQuery.sizeOf;

/**
 * Takes screenshots of the desktop and AWT or Swing {@code Component}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker implements ScreenshotTakerIF {
  private final Robot robot;
  private final ImageFileWriter writer;

  /**
   * Creates a new {@link ScreenshotTaker}.
   *
   * @throws ImageException if an AWT Robot (the responsible for taking screenshots) cannot be instantiated.
   */
  public ScreenshotTaker() {
    this(new ImageFileWriter(), new RobotFactory());
  }

  // Used for tests
  ScreenshotTaker(@Nonnull ImageFileWriter writer, @Nonnull RobotFactory robotFactory) {
    this.writer = writer;
    try {
      robot = robotFactory.newRobotInLeftScreen();
    } catch (AWTException e) {
      throw new ImageException("Unable to create AWT Robot", e);
    }
  }

  @Override
  public void saveDesktopAsPng(String imageFilePath) {
    saveImage(takeDesktopScreenshot(), imageFilePath);
  }

  @Override
  public BufferedImage takeDesktopScreenshot() {
    Rectangle r = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    return takeScreenshot(r);
  }

  @Override
  public void saveComponentAsPng(@Nonnull Component c, @Nonnull String imageFilePath) {
    saveImage(takeScreenshotOf(c), imageFilePath);
  }

  @Override
  @Nonnull public BufferedImage takeScreenshotOf(@Nonnull Component c) {
    Point locationOnScreen = locationOnScreen(c);
    Dimension size = sizeOf(c);
    Rectangle r = new Rectangle(locationOnScreen.x, locationOnScreen.y, size.width, size.height);
    return takeScreenshot(r);
  }

  @Nonnull private BufferedImage takeScreenshot(Rectangle r) {
    JTextComponent textComponent = findFocusOwnerAndHideItsCaret();
    robot.waitForIdle();
    try {
      return takeScreenshot(robot, r);
    } finally {
      showCaretIfPossible(textComponent);
    }
  }

  @RunsInEDT
  private static JTextComponent findFocusOwnerAndHideItsCaret() {
    return execute(() -> {
      Component focusOwner = focusOwner();
      if (!(focusOwner instanceof JTextComponent)) {
        return null;
      }
      JTextComponent textComponent = (JTextComponent) focusOwner;
      Caret caret = textComponent.getCaret();
      if (caret == null || !caret.isVisible()) {
        return null;
      }
      caret.setVisible(false);
      return textComponent;
    });
  }

  // TODO(Alex): Verify that this method really needs to be executed in the EDT.
  @Nonnull private static BufferedImage takeScreenshot(final @Nonnull Robot robot, final @Nonnull Rectangle r) {
    BufferedImage result = execute(() -> robot.createScreenCapture(r));
	  return Objects.requireNonNull(result);
  }

  private void showCaretIfPossible(@Nullable JTextComponent textComponent) {
    if (textComponent == null) {
      return;
    }
    showCaretOf(textComponent);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void showCaretOf(final @Nonnull JTextComponent textComponent) {
    execute(() -> {
      Caret caret = textComponent.getCaret();
      if (caret != null) {
        caret.setVisible(true);
      }
    });
  }

  @Override
  public void saveImage(@Nonnull BufferedImage image, @Nonnull String filePath) {
    Strings.checkNotNullOrEmpty(filePath, "filePath");
    if (!filePath.endsWith(PNG)) {
      String format = String.format("The file in path '%s' should have extension 'png'", filePath);
      throw new IllegalArgumentException(format);
    }
    try {
      writer.writeAsPng(image, filePath);
    } catch (IOException e) {
      String msg = String.format("Unable to save image as '%s'", filePath);
      throw new IORuntimeException(msg, e);
    }
  }
}
