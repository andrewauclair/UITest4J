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
package org.uitest4j.swing.assertions.error;

import org.assertj.core.description.TextDescription;
import org.assertj.core.error.ErrorMessageFactory;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.assertions.error.ShouldHaveDimension.shouldHaveDimension;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for <code>{@link ShouldHaveDimension#create(Description)}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ShouldHaveDimension_create_Test {

  private ErrorMessageFactory factory;

  @BeforeEach
  void setUp() {
    factory = shouldHaveDimension(newArrayList("Luke", "Yoda"), new Dimension(12, 20), new Dimension(42, 15));
  }

  @Test
  void should_Create_Error_Message() {
    String message = factory.create(new TextDescription("Test"), new StandardRepresentation());
    assertEquals("[Test] expected size:<42x15> but was:<12x20> in:<[\"Luke\", \"Yoda\"]>", message);
  }
}
