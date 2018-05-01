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
package org.uitest4j.swing.util;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * <p>
 * A range (from, to).
 * </p>
 *
 * <p>
 * Usage:
 * </p>
 *
 * <pre>
 * Range range = {@link #from(int) from}(0).{@link #to(int) to}(8);
 * </pre>
 *
 * @author Alex Ruiz
 */
public final class Range {
  /**
   * Creates a new {@link From}, representing the starting value of a range.
   *
   * @param value the starting value of the range.
   * @return the created {@code From}.
   */
  public static @Nonnull From from(int value) {
    return new From(value);
  }

  /**
   * Creates a new {@link To}, representing the ending value of a range.
   *
   * @param value the ending value of the range.
   * @return the created {@code To}.
   */
  public static @Nonnull To to(int value) {
    return new To(value);
  }

  /**
   * Starting value of a range.
   *
   * @see Range
   *
   * @author Alex Ruiz
   */
  public static class From {
    public final int value;

    From(int value) {
      this.value = value;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) {
        return true;
      }
		if (o == null || getClass() != o.getClass()) {
        return false;
      }
		From from = (From) o;
		return value == from.value;
	}

	  @Override
	  public int hashCode() {
		  return Objects.hash(value);
    }
  }

  /**
   * Ending value of a range.
   *
   * @see Range
   *
   * @author Alex Ruiz
   */
  public static class To {
    public final int value;

    To(int value) {
      this.value = value;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) {
        return true;
      }
		if (o == null || getClass() != o.getClass()) {
        return false;
      }
		To to = (To) o;
		return value == to.value;
	}

	  @Override
	  public int hashCode() {

		  return Objects.hash(value);
    }
  }

  private Range() {
  }
}
