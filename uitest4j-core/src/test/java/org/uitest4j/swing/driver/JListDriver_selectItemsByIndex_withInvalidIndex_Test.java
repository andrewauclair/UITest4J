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

import org.uitest4j.swing.test.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static java.lang.String.valueOf;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Strings.concat;

/**
 * Tests for {@link JListDriver#selectItems(javax.swing.JList, int[])}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JListDriver_selectItemsByIndex_withInvalidIndex_Test extends JListDriver_TestCase {
  static Collection<Object[]> indices() {
    return newArrayList(indicesOutOfBounds());
  }

  @ParameterizedTest
  @MethodSource("indices")
  void should_Throw_Error_If_Index_Is_Out_Of_Bounds(int index) {
    showWindow();
    ExpectedException.assertContainsMessage(IndexOutOfBoundsException.class, () -> driver.selectItems(list, new int[] { index }), concat("Item index (", valueOf(index),
        ") should be between [0] and [2] (inclusive)"));
  }
}
