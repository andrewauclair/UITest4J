/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.assertions.data;

import java.util.Objects;

/**
 * Represents a location in (x, y) coordinate space, specified in integer precision.
 *
 * @author Yvonne Wang
 */
public class Point {

  /**
   * Creates a new <code>{@link Point}</code>.
   *
   * @param x the x coordinate.
   * @param y the y coordinate.
   * @return the created {@code Point}.
   */
  public static Point atPoint(int x, int y) {
    return new Point(x, y);
  }

  /** The x coordinate. */
  public final int x;

  /** The y coordinate. */
  public final int y;

  private Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
	  return String.format("[%d, %d]", x, y);
  }

	@Override
	public boolean equals(Object o) {
		if (this == o) {
      return true;
		}
		if (o == null || getClass() != o.getClass()) {
      return false;
		}
		Point point = (Point) o;
		return x == point.x &&
				y == point.y;
  }

  @Override
  public int hashCode() {
	  return Objects.hash(x, y);
  }
}
