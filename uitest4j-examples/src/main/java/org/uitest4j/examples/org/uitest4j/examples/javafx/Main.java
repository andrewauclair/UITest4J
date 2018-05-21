package org.uitest4j.examples.org.uitest4j.examples.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> Platform.exit());
		file.getItems().add(exit);
		menu.getMenus().add(file);
		
		VBox root = new VBox();
		Scene scene = new Scene(root, 400, 350);
		root.getChildren().add(menu);
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("UITest4J JavaFX Examples");
		primaryStage.show();
	}
}
