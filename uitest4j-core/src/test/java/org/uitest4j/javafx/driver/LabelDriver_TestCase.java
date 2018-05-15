package org.uitest4j.javafx.driver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.CountDownLatch;

import static org.uitest4j.javafx.platform.FXGUI.runAndWait;

public class LabelDriver_TestCase {
	Label label;// = new Label("Hi");
	LabelDriver driver;

	Stage stage;

	@BeforeEach
	void beforeEach() {
		driver = new LabelDriver();

		Platform.setImplicitExit(false);

		new JFXPanel();
			label = new Label("Hi");
			label.setUserData("TestLabel");
			StackPane root = new StackPane();
			root.getChildren().add(label);
			Scene scene = new Scene(root, 200, 200);

		runAndWait(() -> {
			stage = new Stage();
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		});
	}

	@AfterEach
	void afterEach() {
		runAndWait(stage::close);
	}

	static class MyApp extends Application {
		final Label label = new Label("Hi");

		static MyApp application;

		static void runApp() {
			launch();
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			application = this;

			StackPane root = new StackPane();
			root.getChildren().add(label);
			primaryStage.setScene(new Scene(root, 200, 200));
			primaryStage.show();
		}
	}
}
