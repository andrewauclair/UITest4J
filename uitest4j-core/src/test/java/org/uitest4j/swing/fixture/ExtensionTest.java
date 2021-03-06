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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.core.Robot;
import org.uitest4j.swing.driver.JComponentDriver;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.test.builder.JTextFields.textField;

/**
 * Tests for issue <a href="http://code.google.com/p/fest/issues/detail?id=109" target="_blank">109</a>: Add support for
 * extension.
 *
 * @author Alex Ruiz
 */
public class ExtensionTest extends RobotBasedTestCase {
	private FrameFixture fixture;

	@Override
	protected void onSetUp() {
		fixture = new FrameFixture(robot, TestWindow.createNewWindow(ExtensionTest.class));
	}

	@Override
	protected void onTearDown() {
		fixture.cleanUp();
	}

	@Test
	void shouldCreateFixtureUsingExtension() {
		JTextFieldFixture textField = fixture.with(JTextFieldFixtureExtension.textFieldWithName("hello"));
		assertThat(textField).isNotNull();
	}

	static class JTextFieldFixtureExtension extends ComponentFixtureExtension<JTextField, JTextFieldFixture> {
		final String name;

		JTextFieldFixtureExtension(String name) {
			this.name = name;
		}

		static JTextFieldFixtureExtension textFieldWithName(String name) {
			return new JTextFieldFixtureExtension(name);
		}

		@Override
		public JTextFieldFixture createFixture(@Nonnull Robot robot, @Nonnull Container root) {
			return new JTextFieldFixture(robot, textField().createNew());
		}
	}

	static class JTextFieldFixture extends AbstractComponentFixture<JTextFieldFixture, JTextField, JComponentDriver> {
		JTextFieldFixture(Robot robot, JTextField target) {
			super(JTextFieldFixture.class, robot, target);
		}

		@Override
		@Nonnull
		protected JComponentDriver createDriver(@Nonnull Robot robot) {
			return new JComponentDriver(robot);
		}
	}
}
