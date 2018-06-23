package org.uitest4j.javafx.fixture;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.uitest4j.javafx.core.BasicFXRobot;
import org.uitest4j.javafx.exception.NodeLookupException;
import org.uitest4j.javafx.test.core.FXRobotBasedTestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.uitest4j.javafx.platform.FXGUIActionRunner.executeFX;

/**
 * @author Andrew Auclair
 */
class StageFixture_constructor_withFXRobotAndName_Test extends FXRobotBasedTestCase {
	private MyStage window;
	
	@Override
	protected void onSetUp() {
		window = MyStage.createNew();
		robot = new BasicFXRobot(window);
	}
	
	@Test
	void should_look_up_showing_stage_by_name() {
		executeFX(window::show);
		StageFixture fixture = new StageFixture(robot, "stage");
		assertThat(fixture.robot()).isSameAs(robot);
		assertThat(fixture.target()).isSameAs(window);
	}
	
	@Test
	void should_throw_error_if_stage_with_matching_name_is_not_showing() {
		assertThrows(NodeLookupException.class, () -> new StageFixture(robot, "stage"));
	}
	
	@Test
	void should_throw_error_if_stage_with_matching_name_is_not_found() {
		assertThrows(NodeLookupException.class, () -> new StageFixture(robot, "other"));
	}
	
	private static class MyStage extends Stage {
		static MyStage createNew() {
			return executeFX(MyStage::new);
		}
		
		MyStage() {
			setUserData("stage");
		}
	}
}
