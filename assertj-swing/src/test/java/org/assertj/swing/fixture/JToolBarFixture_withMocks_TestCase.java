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
package org.assertj.swing.fixture;

import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.JToolBarDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.fixture.JToolBarFixture.UnfloatConstraint.WEST;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link JToolBarFixture}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JToolBarFixture_withMocks_TestCase {
  private JToolBarFixture fixture;

  @BeforeEach
  void setUp() {
    fixture = new JToolBarFixture(mock(Robot.class), mock(JToolBar.class));
    fixture.replaceDriverWith(mock(JToolBarDriver.class));
  }

  @Test
  void should_Call_FloatTo_In_Driver_And_Return_Self() {
    assertThat(fixture.floatTo(new Point(6, 8))).isSameAs(fixture);
    verify(fixture.driver()).floatTo(fixture.target(), 6, 8);
  }

  @Test
  void should_Call_Unfloat_In_Driver_And_Return_Self() {
    assertThat(fixture.unfloat()).isSameAs(fixture);
    verify(fixture.driver()).unfloat(fixture.target());
  }

  @Test
  void should_Call_Unfloat_With_Constraing_In_Driver_And_Return_Self() {
    assertThat(fixture.unfloat(WEST)).isSameAs(fixture);
    verify(fixture.driver()).unfloat(fixture.target(), WEST.value());
  }
}
