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

import org.uitest4j.swing.exception.LocationUnavailableException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.util.Pair;
import org.uitest4j.swing.util.TextMatcher;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;

import javax.swing.table.JTableHeader;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link JTableHeaderLocation#pointAt(JTableHeader, TextMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableHeaderLocation_pointAtColumnWithName_Test extends JTableHeaderLocation_TestCase {
  private TextMatcher matcher;

  @Override
  void extraSetUp() {
    matcher = mock(TextMatcher.class);
  }

  @Test
  void should_Return_Point_At_Column() {
    when(matcher.isMatching("0")).thenReturn(false);
    when(matcher.isMatching("1")).thenReturn(true);
    Pair<Integer, Point> pair = matchingIndexAndPoint();
    int index = pair.first;
    assertThat(index).isEqualTo(1);
    Point point = pair.second;
    assertThat(point).isEqualTo(expectedPoint(1));
  }

  @Test
  void should_Return_View_Index() {
    execute(() -> tableHeader.getTable().moveColumn(0, 1));
    when(matcher.isMatching("0")).thenReturn(false);
    when(matcher.isMatching("1")).thenReturn(true);
    Pair<Integer, Point> pair = matchingIndexAndPoint();
    int index = pair.first;
    assertThat(index).isEqualTo(0);
    Point point = pair.second;
    assertThat(point).isEqualTo(expectedPoint(0));
  }

  @Test
  void should_Throw_Error_If_Matching_Column_Was_Not_Found() {
    when(matcher.isMatching("0")).thenReturn(false);
    when(matcher.isMatching("1")).thenReturn(false);
    when(matcher.description()).thenReturn("text");
    when(matcher.formattedValues()).thenReturn("'Hello'");
    ExpectedException.assertContainsMessage(LocationUnavailableException.class, () -> matchingIndexAndPoint(), "Unable to find column with name matching text 'Hello'");
  }

  @RunsInEDT
  private Pair<Integer, Point> matchingIndexAndPoint() {
    return pointAt(location, tableHeader, matcher);
  }

  @RunsInEDT
  private static Pair<Integer, Point> pointAt(final JTableHeaderLocation l, final JTableHeader h, final TextMatcher m) {
    return execute(() -> l.pointAt(h, m));
  }
}
