/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.core;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.hierarchy.ExistingHierarchy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BasicComponentPrinter#printerWithCurrentAwtHierarchy()}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class BasicComponentPrinter_printerWithCurrentAwtHierarchy_Test {
	@Test
	void should_Create_ComponentPrinter_With_ExistingHierarchy() {
		BasicComponentPrinter printer = (BasicComponentPrinter) BasicComponentPrinter.printerWithCurrentAwtHierarchy();
		assertThat(printer.hierarchy()).isInstanceOf(ExistingHierarchy.class);
	}
}
