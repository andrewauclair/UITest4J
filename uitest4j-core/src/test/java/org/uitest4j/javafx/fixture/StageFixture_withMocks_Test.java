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

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uitest4j.core.api.javafx.FXRobot;
import org.uitest4j.javafx.driver.StageDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Andrew Auclair
 */
class StageFixture_withMocks_Test {
	private StageFixture fixture;
	
	@BeforeEach
	void setUp() {
		fixture = new StageFixture(mock(FXRobot.class), mock(Stage.class));
		fixture.replaceDriverWith(mock(StageDriver.class));
	}
	
	@Test
	void calls_requireTitle_in_driver_and_returns_self() {
		assertThat(fixture.requireTitle("Title")).isSameAs(fixture);
		verify(fixture.driver()).requireTitle(fixture.target(), "Title");
	}
	
	@Test
	void calls_requireApplicationModality_in_driver_and_returns_self() {
		assertThat(fixture.requireApplicationModality()).isSameAs(fixture);
		verify(fixture.driver()).requireApplicationModality(fixture.target());
	}
}
