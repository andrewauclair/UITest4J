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
package org.uitest4j.examples.javafx;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.examples.org.uitest4j.examples.javafx.Main;
import org.uitest4j.javafx.JavaFX;

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;

class Main_UITest {
	Main main;
	private Stage stage;
	
	@BeforeAll
	static void beforeAll() {
		JavaFX.initPlatform();
	}
	
	@BeforeEach
	void beforeEach() {
		main = new Main();
		
		executeFX(() -> {
			stage = new Stage();
			main.start(stage);
			stage.show();
		});
	}
	
	@AfterEach
	void afterEach() {
		executeFX(stage::close);
	}
	
	@Test
	void title_of_javafx_example() {
	
	}
}
