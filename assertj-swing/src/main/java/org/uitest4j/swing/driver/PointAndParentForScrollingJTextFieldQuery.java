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
package org.uitest4j.swing.driver;

import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;


/**
 * <p>
 * Returns the point and parent to use as a reference when scrolling a {@code JTextField} up or down.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT). Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class PointAndParentForScrollingJTextFieldQuery {
  @RunsInCurrentThread
  static @Nonnull Pair<Point, Container> pointAndParentForScrolling(final @Nonnull JTextField textField) {
    Point origin = new Point(textField.getX(), textField.getY());
    Container parent = textField.getParent();
    while (parent != null && !(parent instanceof JComponent) && !(parent instanceof CellRendererPane)) {
      origin = addRectangleToPoint(Objects.requireNonNull(parent.getBounds()), origin);
      parent = parent.getParent();
    }
    return Pair.of(origin, parent);
  }

  @Nonnull private static Point addRectangleToPoint(@Nonnull Rectangle r, @Nonnull Point p) {
    Point newPoint = new Point(p);
    newPoint.x += r.x;
    newPoint.y += r.y;
    return newPoint;
  }

  private PointAndParentForScrollingJTextFieldQuery() {
  }
}
