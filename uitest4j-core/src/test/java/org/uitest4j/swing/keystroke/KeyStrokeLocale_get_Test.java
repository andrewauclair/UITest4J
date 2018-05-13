/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.keystroke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.im.InputContext;
import java.util.Locale;

import static java.util.Locale.setDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.uitest4j.swing.keystroke.KeyStrokeLocale.ASSERTJ_SWING_KEYBOARD_LOCALE;


/**
 * Provides tests for {@link KeyStrokeLocale#get()}.
 *
 * @author Christian Roesch
 */
class KeyStrokeLocale_get_Test {
	private Locale originalLocale;
	private String originalProperty;

	@BeforeEach
	void backupSettings() {
		originalLocale = Locale.getDefault();
		originalProperty = System.getProperty(ASSERTJ_SWING_KEYBOARD_LOCALE);
	}

	@AfterEach
	void restoreSettings() {
		setDefault(originalLocale);
		setProperty(originalProperty);
	}

	@Test
	void should_Return_Locale_From_Property_If_Set_To_German() {
		setProperty("de");

		KeyStrokeLocale.reloadFromSystemSettings();
		assertThat(KeyStrokeLocale.get()).isEqualTo(Locale.GERMAN);
	}

	@Test
	void should_Return_Locale_From_Property_If_Set_To_Italian() {
		setProperty("it");

		KeyStrokeLocale.reloadFromSystemSettings();
		assertThat(KeyStrokeLocale.get()).isEqualTo(Locale.ITALIAN);
	}

	@Test
	void should_Return_Locale_With_String_From_Property() {
		setProperty("abcde");

		KeyStrokeLocale.reloadFromSystemSettings();
		assertThat(KeyStrokeLocale.get()).isEqualTo(new Locale("abcde"));
	}

	@Test
	void should_Return_Locale_From_Input_Context_If_Property_Is_Null() {
		assumeTrue(InputContext.getInstance().getLocale() != null);

		setProperty(null);
		setDefault(Locale.ITALIAN);

		KeyStrokeLocale.reloadFromSystemSettings();
		assertThat(KeyStrokeLocale.get()).isEqualTo(InputContext.getInstance().getLocale());
	}

	@Test
	void should_Return_Default_Locale_If_Property_Is_Null_And_InputContext_Delivers_Null() {
		assumeTrue(InputContext.getInstance().getLocale() == null);

		setProperty(null);
		setDefault(Locale.ITALIAN);

		KeyStrokeLocale.reloadFromSystemSettings();
		assertThat(KeyStrokeLocale.get()).isEqualTo(Locale.ITALIAN);
	}

	private void setProperty(String value) {
		if (value == null) {
			System.clearProperty(ASSERTJ_SWING_KEYBOARD_LOCALE);
		}
		else {
			System.setProperty(ASSERTJ_SWING_KEYBOARD_LOCALE, value);
		}
	}
}
