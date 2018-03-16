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
package org.assertj.swing.launcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AppletParameterBuilder#value(String)}.
 * 
 * @author Yvonne Wang
 */
public class AppletParameterBuilder_value_Test {
  @Test
  public void should_Create_AppletParameter() {
    AppletParameter parameter = AppletParameter.name("bgcolor").value("blue");
    assertThat(parameter.name).isEqualTo("bgcolor");
    assertThat(parameter.value).isEqualTo("blue");
  }
}
