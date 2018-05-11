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

/**
 * Tests for <code>{@link XmlNode#text()}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlNode_text_Test extends XmlNode_TestCase {

	@Test
	public void should_Return_Text() {
		node.addText("Hello");
		assertEquals("Hello", node.text());
	}
}
