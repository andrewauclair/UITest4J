/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.driver;

import org.junit.jupiter.api.function.Executable;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.cell.JTableCellWriter;
import org.uitest4j.exception.ActionFailedException;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;

import javax.swing.*;
import java.awt.*;

import static org.uitest4j.swing.driver.JTableCellValueQuery.cellValueOf;

/**
 * Test case for implementations of {@link JTableCellWriter}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class JTableCellWriter_TestCase extends RobotBasedTestCase {
	TableDialogEditDemoWindow window;
	JTable table;
	JTableCellWriter writer;

	@Override
	protected final void onSetUp() {
		writer = createWriter();
		window = TableDialogEditDemoWindow.createNew(getClass());
		table = window.table;
		robot.showWindow(window, new Dimension(500, 100));
	}

	abstract JTableCellWriter createWriter();

	final void assertActionFailedExceptionWithMessageIndicatingWriterWasUnableToActivateEditor(Executable executable) {
//    thrown.expect(ActionFailedException.class, "Unable to find or activate editor");
		ExpectedException.assertContainsMessage(ActionFailedException.class, executable, "Unable to find or activate editor");
	}

	@RunsInEDT
	final Object valueAt(int row, int column) {
		return cellValueOf(window.table, row, column);
	}
}
