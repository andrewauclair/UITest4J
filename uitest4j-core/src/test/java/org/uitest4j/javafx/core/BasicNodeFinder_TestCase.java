/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.javafx.core;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.uitest4j.javafx.test.TestStage;

import javax.annotation.Nonnull;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;


/**
 * @author Andrew Auclair
 */
class BasicNodeFinder_TestCase {
	BasicNodeFinder finder;
	MyStage stage;

	@BeforeAll
	static void beforeAll() {
		// Prime the JavaFX Platform
		Platform.setImplicitExit(false);
		new JFXPanel();
	}

	@BeforeEach
	void beforeEach() {
		finder = BasicNodeFinder.finderWithCurrentWindowHierarchy();
		stage = MyStage.createNew(getClass());
	}

	@AfterEach
	void afterEach() {
		executeFX(stage::close);
	}

	static class MyStage extends TestStage {
		final Button button = new Button("A Button");
		final Label label = new Label("A Label");
		final TextField textField1 = new TextField("TextField 1");
		final TextField textField2 = new TextField("TextField 2");

		static MyStage createNew(final Class<?> testClass) {
			return executeFX(() -> new MyStage(testClass));
		}

		private MyStage(@Nonnull Class<?> testClass) {
			super(testClass);
			StackPane root = new StackPane();
			root.getChildren().addAll(button, label, textField1, textField2);

			label.setLabelFor(button);
			button.setUserData("button");

			Scene scene = new Scene(root, 200, 200);
			setScene(scene);
			centerOnScreen();
			show();
		}
	}
}
