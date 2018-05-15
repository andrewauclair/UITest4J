package org.uitest4j.javafx.driver;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.test.ExpectedException;

import java.util.regex.Pattern;

class LabelDriver_requireTextAsPattern_Test extends LabelDriver_TestCase {
	@Test
	void should_Pass_If_Text_Matches_Pattern() {
		driver.requireText(label, Pattern.compile("H.*"));
	}

	@Test
	void should_Fail_If_Text_Does_Not_Match_Pattern() {
		Pattern pattern = Pattern.compile("B.*");
		ExpectedException.assertOpenTest4jError(() -> driver.requireText(label, pattern), "Expected text of 'TestLabel' to match pattern 'B.*' but was 'Hi'", pattern, "Hi");
	}
}
