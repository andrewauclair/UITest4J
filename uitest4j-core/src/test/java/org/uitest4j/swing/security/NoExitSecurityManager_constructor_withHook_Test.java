/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.security;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

/**
 * Tests for {@link NoExitSecurityManager#NoExitSecurityManager(ExitCallHook)}.
 *
 * @author Alex Ruiz
 */
class NoExitSecurityManager_constructor_withHook_Test {
	@Test
	void should_Throw_Error_If_Hook_Is_Null() {
		ExpectedException.assertContainsMessage(NullPointerException.class, () -> new NoExitSecurityManager(null), "The given ExitCallHook should not be null");
	}
}
