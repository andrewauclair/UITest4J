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
package org.uitest4j.swing.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JLabels.label;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

import java.awt.Component;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ComponentLookupException#found()}.
 * 
 * @author Alex Ruiz
 */
public class ComponentLookupException_found_Test {
  @Test
  public void should_Return_Copy_Of_Found_Components() {
    List<Component> found = Lists.newArrayList();
    found.add(label().createNew());
    found.add(textField().createNew());
    ComponentLookupException e = new ComponentLookupException("Hello", found);
    assertThat(e.found()).isNotSameAs(found).containsExactlyElementsOf(found);
  }
}
