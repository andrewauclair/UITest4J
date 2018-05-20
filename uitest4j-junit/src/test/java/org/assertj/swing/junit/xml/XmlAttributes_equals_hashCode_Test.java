/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.junit.xml;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for <code>{@link XmlAttributes#equals(Object)}</code> and <code>{@link XmlAttributes#hashCode()}</code>.
 *
 * @author Christian Rösch
 * @author Andrew Auclair
 */
public class XmlAttributes_equals_hashCode_Test {

	@Test
	public void should_Be_Correct() {
		assertEquals(XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")));
		assertNotEquals(XmlAttributes.attributes(xml("Test2", "Value"), xml("Test2", "Value2")), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")));
		assertNotEquals(XmlAttributes.attributes(xml("Test", "Value2"), xml("Test2", "Value2")), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")));
		assertNotEquals("Test", XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")));

		assertEquals(XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")).hashCode(), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")).hashCode());
		assertNotEquals(XmlAttributes.attributes(xml("Test2", "Value"), xml("Test2", "Value2")).hashCode(), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")).hashCode());
		assertNotEquals(XmlAttributes.attributes(xml("Test", "Value2"), xml("Test2", "Value2")).hashCode(), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")).hashCode());
		assertNotEquals("Test".hashCode(), XmlAttributes.attributes(xml("Test", "Value"), xml("Test2", "Value2")).hashCode());
	}

	private XmlAttribute xml(String name, String value) {
		return new XmlAttribute(name, value);
	}
}
