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
package org.assertj.swing.junit.xml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.junit.xml.XmlAttribute.name;

import java.util.Collection;

/**
 * Tests for <code>{@link XmlAttributes#equals(Object)}</code>.
 * 
 * @author Alex Ruiz
 */
class XmlAttributes_equals_Test {

  private XmlAttributes attributes;

  private static Collection<Object[]> notEqualAttributes() {
    return newArrayList(new Object[][] {
        { XmlAttributes.attributes(name("firstName").value("Han"), name("lastName").value("Solo")) },
        { XmlAttributes.attributes() }, { XmlAttributes.attributes(name("name").value("Yoda")) } });
  }

  @BeforeEach
  void setUp() {
    attributes = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  @ParameterizedTest
  @MethodSource("notEqualAttributes")
  void shouldNotBeEqualToDifferentObject(XmlAttributes notEqualAttributes) {
    assertThat(attributes.equals(notEqualAttributes)).isFalse();
  }
}
