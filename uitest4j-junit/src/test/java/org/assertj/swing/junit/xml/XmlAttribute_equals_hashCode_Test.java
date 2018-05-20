/*
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
 * Tests for <code>{@link XmlAttribute#equals(Object)}</code> and <code>{@link XmlAttribute#hashCode()}</code>.
 *
 * @author Christian Rösch
 * @author Andrew Auclair
 */
public class XmlAttribute_equals_hashCode_Test {

	@Test
	public void should_Be_Correct() {
		assertEquals(new XmlAttribute("Test", "Value"), new XmlAttribute("Test", "Value"));
		assertNotEquals(new XmlAttribute("Test", "Value"), new XmlAttribute("Test2", "Value"));
		assertNotEquals(new XmlAttribute("Test", "Value"), new XmlAttribute("Test", "Value2"));
		assertNotEquals("Test", new XmlAttribute("Test", "Value"));

		assertEquals(new XmlAttribute("Test", "Value").hashCode(), new XmlAttribute("Test", "Value").hashCode());
		assertNotEquals(new XmlAttribute("Test", "Value").hashCode(), new XmlAttribute("Test2", "Value").hashCode());
		assertNotEquals(new XmlAttribute("Test", "Value").hashCode(), new XmlAttribute("Test", "Value2").hashCode());
		assertNotEquals("Test".hashCode(), new XmlAttribute("Test", "Value").hashCode());
	}
}
