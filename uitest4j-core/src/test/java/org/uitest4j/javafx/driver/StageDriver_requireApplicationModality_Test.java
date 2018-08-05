/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.javafx.driver;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.uitest4j.javafx.core.BasicFXRobot_TestCase;
import org.uitest4j.javafx.platform.FXGUIActionRunner;
import org.uitest4j.swing.test.ExpectedException;

import java.util.Objects;

/**
 * @author Andrew Auclair
 */
class StageDriver_requireApplicationModality_Test extends BasicFXRobot_TestCase {
	@Test
	void passes_when_stage_title_matches() {
		Stage appModalStage = Objects.requireNonNull(FXGUIActionRunner.executeFX(() -> {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			return stage;
		}));
		new StageDriver().requireApplicationModality(appModalStage);
	}
	
	@Test
	void fails_when_stage_title_does_not_match() {
		ExpectedException.assertOpenTest4jError(() -> new StageDriver().requireApplicationModality(stage()), "Expected modality of 'TestStage' to be APPLICATION_MODAL but was NONE", Modality.APPLICATION_MODAL, Modality.NONE);
	}
}
