package org.uitest4j.javafx.driver;

import javafx.scene.control.Label;
import org.uitest4j.driver.TextDisplayDriver;
import org.uitest4j.swing.internal.assertions.OpenTest4JAssertions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.execute;

public class LabelDriver implements TextDisplayDriver<Label> {
	@Override
	public void requireText(@Nonnull Label labeled, @Nullable String expected) {
		OpenTest4JAssertions.assertEquals(expected, textOf(labeled), () -> "Expected text of '" + labeled.getUserData() +
				"' to be '" + expected + "' but was '" + textOf(labeled) + "'");
	}

	@Override
	public void requireText(@Nonnull Label labeled, @Nonnull Pattern pattern) {
		OpenTest4JAssertions.assertMatchesPattern(pattern, textOf(labeled), () -> "Expected text of '" + labeled.getUserData() +
				"' to match pattern '" + pattern.toString() + "' but was '" + textOf(labeled) + "'");
	}

	@Nullable
	@Override
	public String textOf(@Nonnull Label labeled) {
		return execute(labeled::getText);
	}
}
