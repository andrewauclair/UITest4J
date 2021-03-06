/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.util.OSIdentifierStub.macintosh;
import static org.uitest4j.swing.util.OSIdentifierStub.windows9x;

/**
 * Tests for {@link Platform#isWindows9x()}.
 *
 * @author Alex Ruiz
 */
class Platform_isWindows9x_Test extends Platform_TestCase {
	@Test
	void should_Return_True_If_OS_Is_Windows9x() {
		Platform.initialize(windows9x(), toolkit);
		assertThat(Platform.isWindows9x()).isTrue();
	}

	@Test
	void should_Return_False_If_OS_Is_Not_Windows9x() {
		Platform.initialize(macintosh(), toolkit);
		assertThat(Platform.isWindows9x()).isFalse();
	}
}
