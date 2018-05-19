/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.fixture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.KeyPressInfo;
import org.uitest4j.swing.core.MouseClickInfo;
import org.uitest4j.core.api.swing.Robot;
import org.uitest4j.swing.driver.ComponentDriver;
import org.uitest4j.swing.timing.Timeout;

import javax.annotation.Nonnull;
import java.awt.*;

import static java.awt.Color.BLUE;
import static java.awt.event.KeyEvent.VK_C;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.uitest4j.swing.core.KeyPressInfo.keyCode;
import static org.uitest4j.swing.core.MouseButton.LEFT_BUTTON;
import static org.uitest4j.swing.core.MouseClickInfo.leftButton;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Tests for {@link AbstractComponentFixture}.
 *
 * @author Alex Ruiz
 */
class AbstractComponentFixture_withMocks_Test {
	private ComponentFixture fixture;

	@BeforeEach
	void setUp() {
		fixture = new ComponentFixture();
	}

	@Test
	void should_Call_Click_In_Driver_And_Return_Self() {
		assertThat(fixture.click()).isSameAs(fixture);
		verify(fixture.driver()).click(fixture.target());
	}

	@Test
	void should_Call_Click_With_MouseButton_In_Driver_And_Return_Self() {
		assertThat(fixture.click(LEFT_BUTTON)).isSameAs(fixture);
		verify(fixture.driver()).click(fixture.target(), LEFT_BUTTON);
	}

	@Test
	void should_Call_Click_With_MouseClickInfo_In_Driver_And_Return_Self() {
		MouseClickInfo clickInfo = leftButton().times(2);
		assertThat(fixture.click(clickInfo)).isSameAs(fixture);
		verify(fixture.driver()).click(fixture.target(), clickInfo);
	}

	@Test
	void should_Call_DoubleClick_In_Driver_And_Return_Self() {
		assertThat(fixture.doubleClick()).isSameAs(fixture);
		verify(fixture.driver()).doubleClick(fixture.target());
	}

	@Test
	void should_Call_RightClick_In_Driver_And_Return_Self() {
		assertThat(fixture.rightClick()).isSameAs(fixture);
		verify(fixture.driver()).rightClick(fixture.target());
	}

	@Test
	void should_Call_Focus_In_Driver_And_Return_Self() {
		assertThat(fixture.focus()).isSameAs(fixture);
		verify(fixture.driver()).focus(fixture.target());
	}

	@Test
	void should_Call_PressAndReleaseKey_In_Driver_And_Return_Self() {
		KeyPressInfo info = keyCode(VK_C);
		assertThat(fixture.pressAndReleaseKey(info)).isSameAs(fixture);
		verify(fixture.driver()).pressAndReleaseKey(fixture.target(), info);
	}

	@Test
	void should_Call_PressAndReleaseKeys_In_Driver_And_Return_Self() {
		int[] keyCodes = {VK_C};
		assertThat(fixture.pressAndReleaseKeys(keyCodes)).isSameAs(fixture);
		verify(fixture.driver()).pressAndReleaseKeys(fixture.target(), keyCodes);
	}

	@Test
	void should_Call_PressKey_In_Driver_And_Return_Self() {
		assertThat(fixture.pressKey(VK_C)).isSameAs(fixture);
		verify(fixture.driver()).pressKey(fixture.target(), VK_C);
	}

	@Test
	void should_Call_PressKeyWhileRunning_In_Driver_And_Return_Self() {
		Runnable runnable = mock(Runnable.class);
		assertThat(fixture.pressKeyWhileRunning(VK_C, runnable)).isSameAs(fixture);
		verify(fixture.driver()).pressKeyWhileRunning(fixture.target(), VK_C, runnable);
	}

	@Test
	void should_Call_ReleaseKey_In_Driver_And_Return_Self() {
		assertThat(fixture.releaseKey(VK_C)).isSameAs(fixture);
		verify(fixture.driver()).releaseKey(fixture.target(), VK_C);
	}

	@Test
	void should_Call_RequireFocused_In_Driver_And_Return_Self() {
		assertThat(fixture.requireFocused()).isSameAs(fixture);
		verify(fixture.driver()).requireFocused(fixture.target());
	}

	@Test
	void should_Call_RequireEnabled_In_Driver_And_Return_Self() {
		assertThat(fixture.requireEnabled()).isSameAs(fixture);
		verify(fixture.driver()).requireEnabled(fixture.target());
	}

	@Test
	void should_Call_RequireEnabled_With_Timeout_In_Driver_And_Return_Self() {
		Timeout timeout = timeout(100);
		assertThat(fixture.requireEnabled(timeout)).isSameAs(fixture);
		verify(fixture.driver()).requireEnabled(fixture.target(), timeout);
	}

	@Test
	void should_Call_RequireDisabled_In_Driver_And_Return_Self() {
		assertThat(fixture.requireDisabled()).isSameAs(fixture);
		verify(fixture.driver()).requireDisabled(fixture.target());
	}

	@Test
	void should_Call_RequireVisible_In_Driver_And_Return_Self() {
		assertThat(fixture.requireVisible()).isSameAs(fixture);
		verify(fixture.driver()).requireVisible(fixture.target());
	}

	@Test
	void should_Call_RequireNotVisible_In_Driver_And_Return_Self() {
		assertThat(fixture.requireNotVisible()).isSameAs(fixture);
		verify(fixture.driver()).requireNotVisible(fixture.target());
	}

	@Test
	void should_Delegate_To_FontOf_In_Driver() {
		Font font = mock(Font.class);
		ComponentDriver driver = fixture.driver();
		Component component = fixture.target();
		when(driver.fontOf(component)).thenReturn(font);
		assertThat(fixture.font().target()).isSameAs(font);
		verify(driver).fontOf(component);
	}

	@Test
	void should_Delegate_To_BackgroundOf_In_Driver() {
		ComponentDriver driver = fixture.driver();
		Component component = fixture.target();
		when(driver.backgroundOf(component)).thenReturn(BLUE);
		assertThat(fixture.background().target()).isSameAs(BLUE);
		verify(driver).backgroundOf(component);
	}

	@Test
	void should_Delegate_To_ForegroundOf_In_Driver() {
		ComponentDriver driver = fixture.driver();
		Component component = fixture.target();
		when(driver.foregroundOf(component)).thenReturn(BLUE);
		assertThat(fixture.foreground().target()).isSameAs(BLUE);
		verify(driver).foregroundOf(component);
	}

	private static class ComponentFixture extends AbstractComponentFixture<ComponentFixture, Component, ComponentDriver> {
		ComponentFixture() {
			super(ComponentFixture.class, mock(Robot.class), mock(Component.class));
		}

		@Override
		@Nonnull
		protected ComponentDriver createDriver(@Nonnull Robot robot) {
			return mock(ComponentDriver.class);
		}
	}
}
