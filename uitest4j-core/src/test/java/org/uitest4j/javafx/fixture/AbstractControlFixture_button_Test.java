package org.uitest4j.javafx.fixture;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.uitest4j.javafx.test.FXTestApp;

/**
 * @author Andrew Auclair
 */
public class AbstractControlFixture_button_Test {

	private static class MyApp extends FXTestApp {
		final Button button = new Button("Click Me");

		static MyApp createNew(final Class<?> testClass) {
			Platform.runLater(MyApp::new);
			return null;
		}

		private MyApp() {

		}

		@Override
		public void start(Stage primaryStage) throws Exception {

		}
	}
}
