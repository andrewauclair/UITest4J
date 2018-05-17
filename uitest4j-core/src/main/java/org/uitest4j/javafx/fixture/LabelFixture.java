package org.uitest4j.javafx.fixture;

import javafx.scene.control.Label;
import org.uitest4j.fixture.TextDisplayFixture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * @author Andrew Auclair
 */
public class LabelFixture<S, T extends Label> implements TextDisplayFixture<S> {
	@Nullable
	@Override
	public String text() {
		return null;
	}
	
	@Nonnull
	@Override
	public S requireText(@Nullable String expected) {
		return null;
	}
	
	@Nonnull
	@Override
	public S requireText(@Nonnull Pattern pattern) {
		return null;
	}
}
