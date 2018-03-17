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
package org.assertj.swing.input;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

import static org.assertj.swing.test.builder.JTextFields.textField;

/**
 * Base test case for {@link DragDropInfo}.
 * 
 * @author Yvonne Wang
 */
public abstract class DragDropInfo_TestCase extends EDTSafeTestCase {
  Component source;
  Point origin;
  long when;
  DragDropInfo info;

  @BeforeEach
  public void setUp() {
    source = textField().createNew();
    origin = new Point(6, 8);
    when = System.currentTimeMillis();
    info = new DragDropInfo();
  }
}
