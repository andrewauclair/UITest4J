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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.uitest4j.javafx.test.core.FXRobotBasedTestCase;
import org.uitest4j.swing.test.recorder.ClickRecorderManager;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;

/**
 * @author Andrew Auclair
 */
public class BasicFXRobot_TestCase extends
		FXRobotBasedTestCase {
	public ClickRecorderManager clickRecorder = new ClickRecorderManager();

	Button button;

	private Stage stage;

	public Stage stage() {
		return stage;
	}
	
	@Override
	protected void onSetUp() {
		button = new Button("Hello");
		button.setUserData("TestButton");
		StackPane root = new StackPane();
		root.getChildren().add(button);
		Scene scene = new Scene(root, 200, 200);

		executeFX(() -> {
			stage = new Stage();
			stage.setUserData("TestStage");
			stage.setTitle(this.getClass().getName());
			robot = new BasicFXRobot(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		});
	}

	@Override
	protected void onTearDown() {
		executeFX(stage::close);
	}
}
