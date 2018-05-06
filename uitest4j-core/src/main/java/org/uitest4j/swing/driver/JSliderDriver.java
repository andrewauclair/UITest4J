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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.edt.GuiQuery;
import org.uitest4j.swing.internal.annotation.InternalApi;
import org.uitest4j.swing.util.GenericRange;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.uitest4j.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.uitest4j.swing.driver.JSliderSetValueTask.setValue;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * <p>
 * Supports functional testing of {@code JSlider}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.uitest4j.swing.fixture} in your tests.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public class JSliderDriver extends JComponentDriver {
  private final JSliderLocation location;

  /**
   * Creates a new {@link JSliderDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public JSliderDriver(@Nonnull Robot robot) {
    super(robot);
    location = new JSliderLocation();
  }

  /**
   * Slides the knob to its maximum.
   * 
   * @param slider the target {@code JSlider}.
   * @throws IllegalStateException if the {@code JSlider} is disabled.
   * @throws IllegalStateException if the {@code JSlider} is not showing on the screen.
   */
  @RunsInEDT
  public void slideToMaximum(@Nonnull JSlider slider) {
    slide(slider, findSlideToMaximumInfo(slider, location()));
  }

  @RunsInEDT
  @Nonnull private static Pair<Integer, GenericRange<Point>> findSlideToMaximumInfo(final @Nonnull JSlider slider,
      final @Nonnull JSliderLocation location) {
    Pair<Integer, GenericRange<Point>> result = execute(new GuiQuery<>() {
      @Override
      protected Pair<Integer, GenericRange<Point>> executeInEDT() {
        checkEnabledAndShowing(slider);
        int value = slider.getMaximum();
        GenericRange<Point> fromAndTo = slideInfo(slider, location, value);
        return Pair.of(value, fromAndTo);
      }
    });
    return Objects.requireNonNull(result);
  }

  /**
   * Slides the knob to its minimum.
   * 
   * @param slider the target {@code JSlider}.
   * @throws IllegalStateException if the {@code JSlider} is disabled.
   * @throws IllegalStateException if the {@code JSlider} is not showing on the screen.
   */
  @RunsInEDT
  public void slideToMinimum(@Nonnull JSlider slider) {
    slide(slider, findSlideToMinimumInfo(slider, location()));
  }

  @RunsInEDT
  @Nonnull private static Pair<Integer, GenericRange<Point>> findSlideToMinimumInfo(final @Nonnull JSlider slider,
      final @Nonnull JSliderLocation location) {
    Pair<Integer, GenericRange<Point>> result = execute(new GuiQuery<>() {
      @Override
      protected Pair<Integer, GenericRange<Point>> executeInEDT() {
        checkEnabledAndShowing(slider);
        int value = slider.getMinimum();
        GenericRange<Point> fromAndTo = slideInfo(slider, location, value);
        return Pair.of(value, fromAndTo);
      }
    });
    return Objects.requireNonNull(result);
  }

  @RunsInEDT
  private void slide(@Nonnull JSlider slider, @Nonnull Pair<Integer, GenericRange<Point>> slideInfo) {
    slide(slider, slideInfo.first, Objects.requireNonNull(slideInfo.second));
  }

  /**
   * Slides the knob to the requested value.
   * 
   * @param slider the target {@code JSlider}.
   * @param value the requested value.
   * @throws IllegalStateException if the {@code JSlider} is disabled.
   * @throws IllegalStateException if the {@code JSlider} is not showing on the screen.
   * @throws IllegalArgumentException if the given position is not within the {@code JSlider} bounds.
   */
  @RunsInEDT
  public void slide(@Nonnull JSlider slider, int value) {
    GenericRange<Point> slideInfo = findSlideInfo(slider, location(), value);
    slide(slider, value, slideInfo);
  }

  @RunsInEDT
  private void slide(@Nonnull JSlider slider, int value, @Nonnull GenericRange<Point> fromAndTo) {
    moveMouseIgnoringAnyError(slider, fromAndTo.from());
    moveMouseIgnoringAnyError(slider, fromAndTo.to());
    setValue(slider, value);
    robot.waitForIdle();
  }

  @RunsInEDT
  @Nonnull private static GenericRange<Point> findSlideInfo(final @Nonnull JSlider slider,
      final @Nonnull JSliderLocation location, final int value) {
    GenericRange<Point> result = execute(new GuiQuery<>() {
      @Override
      protected GenericRange<Point> executeInEDT() {
        checkValueInBounds(slider, value);
        checkEnabledAndShowing(slider);
        return slideInfo(slider, location, value);
      }
    });
    return Objects.requireNonNull(result);
  }

  @RunsInCurrentThread
  private static void checkValueInBounds(@Nonnull JSlider slider, int value) {
    int min = slider.getMinimum();
    int max = slider.getMaximum();
    if (value >= min && value <= max) {
      return;
    }
    String msg = String.format("Value <%d> is not within the JSlider bounds of <%d> and <%d>", value, min, max);
    throw new IllegalArgumentException(msg);
  }

  @RunsInCurrentThread
  private static GenericRange<Point> slideInfo(@Nonnull JSlider slider, JSliderLocation location, int value) {
    Point from = location.pointAt(slider, slider.getValue());
    Point to = location.pointAt(slider, value);
    return new GenericRange<>(from, to);
  }

  @Nonnull private JSliderLocation location() {
    return location;
  }
}
