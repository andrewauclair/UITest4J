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
package org.uitest4j.javafx.fixture;

import javafx.stage.Stage;
import org.uitest4j.core.api.javafx.FXRobot;
import org.uitest4j.javafx.driver.StageDriver;
import org.uitest4j.swing.assertions.Assertions;
import org.uitest4j.swing.fixture.FrameFixture;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author Andrew Auclair
 */
public class StageFixture {
	private FXRobot robot;
	private Stage stage;
	private StageDriver driver = new StageDriver();
	
	public FXRobot robot() {
		return robot;
	}
	
	public Stage target() {
		return stage;
	}
	
	StageFixture(@Nonnull FXRobot robot, String stageName) {
		this.robot = robot;
	}
	
	public StageFixture(@Nonnull FXRobot robot, Stage stage) {
		this.robot = robot;
		this.stage = stage;
	}
	
	public StageDriver driver() {
		return driver;
	}
	
	public final void replaceDriverWith(@Nonnull StageDriver driver) {
		this.driver = Objects.requireNonNull(driver);
	}
	
	@Nonnull
	public StageFixture requireTitle(String expected) {
		driver().requireTitle(target(), expected);
		return this;
	}
}
