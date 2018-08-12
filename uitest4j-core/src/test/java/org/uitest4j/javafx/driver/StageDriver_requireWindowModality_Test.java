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

import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;

/**
 * @author Andrew Auclair
 */
class StageDriver_requireWindowModality_Test extends BasicFXRobot_TestCase {
	@Test
	void passes_when_modality_is_window_modal() {
		Stage appModalStage = Objects.requireNonNull(executeFX(() -> {
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.show();
			return stage;
		}));
		try {
			new StageDriver().requireWindowModality(appModalStage);
		}
		finally {
			executeFX(appModalStage::close);
		}
	}
	
	@Test
	void fails_when_modality_does_not_match() {
		ExpectedException.assertOpenTest4jError(() -> new StageDriver().requireWindowModality(stage()), "Expected modality of 'TestStage' to be WINDOW_MODAL but was NONE", Modality.WINDOW_MODAL, Modality.NONE);
	}
}
