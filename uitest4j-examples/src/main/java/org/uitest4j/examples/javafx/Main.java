package org.uitest4j.examples.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		MenuBar menu = new MenuBar();
		Menu file = new Menu("_File");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> Platform.exit());
		file.getItems().add(exit);
		menu.getMenus().add(file);
		
		Button buttonColor = new Button("Color");
		
		String darkTheme = getClass().getResource("/themes/dark-theme.css").toExternalForm();
		String lightTheme = getClass().getResource("/themes/light-theme.css").toExternalForm();
		
		VBox root = new VBox();
		Scene scene = new Scene(root, 400, 350);
		scene.getStylesheets().add(lightTheme);
		
		Menu style = new Menu("_Style");
		RadioMenuItem light = new RadioMenuItem("Light");
		RadioMenuItem dark = new RadioMenuItem("dark");
		light.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		dark.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
		light.setSelected(true);
		
		ToggleGroup group = new ToggleGroup();
		light.setToggleGroup(group);
		dark.setToggleGroup(group);
		
		if (getClass().getResource("/themes/dark-theme.css") == null) {
			System.out.println("Unable to find dark.css");
		}
		
		light.setOnAction(e -> {
			if (scene.getStylesheets().contains(darkTheme)) {
				scene.getStylesheets().remove(darkTheme);
			}
			scene.getStylesheets().add(lightTheme);
		});
		
		dark.setOnAction(e -> {
			if (scene.getStylesheets().contains(lightTheme)) {
				scene.getStylesheets().remove(lightTheme);
			}
			scene.getStylesheets().add(darkTheme);
		});
		
		style.getItems().addAll(light, dark);
		menu.getMenus().add(style);
		
		root.getChildren().addAll(menu, buttonColor);
		
		SplitMenuButton split = new SplitMenuButton();
		split.getItems().addAll(new MenuItem("One"), new MenuItem("Two"));
		
		root.getChildren().addAll(new Label("Label"), new RadioButton("Radio Button"), new CheckBox("Checkbox"),
				new ToggleButton("Toggle"), split);
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("UITest4J JavaFX Examples");
		primaryStage.show();
	}
}
