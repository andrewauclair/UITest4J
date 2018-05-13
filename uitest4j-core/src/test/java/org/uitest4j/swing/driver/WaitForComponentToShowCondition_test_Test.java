/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.EDTSafeTestCase;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link WaitForComponentToShowCondition#test()}.
 *
 * @author Yvonne Wang
 */
class WaitForComponentToShowCondition_test_Test extends EDTSafeTestCase {
	private WaitForComponentToShowCondition condition;
	private ComponentStub c;

	@BeforeEach
	void setUp() {
		c = ComponentStub.createNew();
		condition = WaitForComponentToShowCondition.untilIsShowing(c);
	}

	@Test
	void should_Return_True_If_Component_Is_Showing() {
		c.showing(true);
		assertThat(condition.test()).isTrue();
	}

	@Test
	void should_Return_False_If_Component_Is_Not_Showing() {
		assertThat(condition.test()).isFalse();
	}

	private static class ComponentStub extends JTextField {
		private boolean showing;

		@RunsInEDT
		static ComponentStub createNew() {
			return execute(ComponentStub::new);
		}

		private ComponentStub() {
		}

		void showing(boolean isShowing) {
			showing = isShowing;
		}

		@Override
		public boolean isShowing() {
			return showing;
		}
	}
}
