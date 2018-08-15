package org.uitest4j.examples.swing;

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.fixture.FrameFixture;
import org.uitest4j.swing.fixture.JToolBarFixture;

class ExampleFrameTest {
	@Test
	void test() {
		ExampleFrame frame = new ExampleFrame();
		
		FrameFixture fixture = new FrameFixture(frame);
		
		JToolBarFixture testToolbar = fixture.toolBar("TestToolbar");
		
		testToolbar.requireVisible();
	}
}
