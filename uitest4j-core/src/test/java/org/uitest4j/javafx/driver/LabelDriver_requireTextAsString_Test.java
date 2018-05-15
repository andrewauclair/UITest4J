package org.uitest4j.javafx.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

class LabelDriver_requireTextAsString_Test extends LabelDriver_TestCase {
	@Test
	void passes_when_text_is_equal_to_expected() {
		driver.requireText(label, "Hi");
	}

	@Test
	void fails_when_text_is_not_equal_to_expected() {
		ExpectedException.assertOpenTest4jError(() -> driver.requireText(label, "Bye"), "Expected text of 'TestLabel' to be 'Bye' but was 'Hi'", "Bye", "Hi");
	}
}
