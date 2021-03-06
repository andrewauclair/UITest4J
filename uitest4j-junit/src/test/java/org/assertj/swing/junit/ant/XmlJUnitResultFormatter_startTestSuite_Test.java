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
package org.assertj.swing.junit.ant;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.assertj.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlJUnitResultFormatter#startTestSuite(JUnitTest)}</code>.
 * 
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter_startTestSuite_Test extends XmlJUnitResultFormatter_TestCase {

  private JUnitTest suite;

  @Override
  public void onSetUp() {
    suite = new JUnitTest("test");
  }

  @Test
  public void should_Write_Suite_And_Environment_Info() {
    formatter.startTestSuite(suite);
    XmlNode root = root();
    assertThat(root.attributeCount()).isEqualTo(3);
    assertThatSuiteAndEnvironmentInfoWereAddedTo(root);
    assertThatThereAreNoPropertiesIn(root);
  }

  @Test
  public void should_Call_Subclass_Hook_When_Starting_Test_Suite() {
    formatter.startTestSuite(suite);
    assertThat(formatter.onStartTestSuiteMethod).wasCalledPassing(suite);
  }
}
