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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.uitest4j.swing.util.OSFamily.WINDOWS;

/**
 * Tests for {@link OSIdentifier#isWindows()}.
 *
 * @author Alex Ruiz
 */
class OSIdentifier_isWindows9x_Test extends OSIdentifier_TestCase {
	private static Collection<Object[]> windows9x() {
		return newArrayList(new Object[][]{{"windows95"}, {"Windows98"}, {"WINDOWSME"}});
	}

	@ParameterizedTest
	@MethodSource("windows9x")
	void should_Return_Is_Windows9x_If_OS_Name_Starts_With_Windows_And_Contains_Any_9x_Version(String windows9x) {
		returnOSName(windows9x);
		OSIdentifier osIdentifier = new OSIdentifier(propertyReader);
		assertThat(osIdentifier.isWindows()).isTrue();
		assertThat(osIdentifier.isWindows9x()).isTrue();
		assertThat(osIdentifier.isHPUX()).isFalse();
		assertThat(osIdentifier.isLinux()).isFalse();
		assertThat(osIdentifier.isMacintosh()).isFalse();
		assertThat(osIdentifier.isOSX()).isFalse();
		assertThat(osIdentifier.isSolaris()).isFalse();
		assertThat(osIdentifier.isWindowsXP()).isFalse();
		assertThat(osIdentifier.isX11()).isFalse();
		assertThat(osIdentifier.osFamily()).isEqualTo(WINDOWS);
	}
}
