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

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for <code>{@link StandardOutputStreams#isStandardOutOrErr(OutputStream)}</code>.
 * 
 * @author Alex Ruiz
 */
public class StandardOutputStreams_isStandardOutOrErr_Test {

  private static StandardOutputStreams streams;

  @BeforeClass
  public static void setUpOnce() {
    streams = new StandardOutputStreams();
  }

  @Test
  public void should_Return_True_If_Out_Is_SystemOut() {
    assertThat(streams.isStandardOutOrErr(System.out)).isTrue();
  }

  @Test
  public void should_Return_True_If_Out_Is_SystemErr() {
    assertThat(streams.isStandardOutOrErr(System.err)).isTrue();
  }

  @Test
  public void should_Return_False_If_Out_Is_Not_Standard() {
    OutputStream out = new ByteArrayOutputStream();
    assertThat(streams.isStandardOutOrErr(out)).isFalse();
  }
}
