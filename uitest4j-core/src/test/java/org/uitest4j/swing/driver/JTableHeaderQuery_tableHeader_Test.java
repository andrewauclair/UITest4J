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

import org.junit.jupiter.api.Test;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestTable;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.table.JTableHeader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link JTableHeaderQuery#tableHeader(javax.swing.JTable)}.
 *
 * @author Alex Ruiz
 */
public class JTableHeaderQuery_tableHeader_Test extends RobotBasedTestCase {
	private MyTable table;

	@Override
	protected void onSetUp() {
		MyWindow window = MyWindow.createNew();
		table = window.table;
	}

	@Test
	public void should_Return_JTableHeader_In_JTable() {
		JTableHeader expected = table.getTableHeader();
		table.startRecording();
		assertThat(JTableHeaderQuery.tableHeader(table)).isSameAs(expected);
		table.requireInvoked("getTableHeader");
	}

	private static class MyWindow extends TestWindow {
		final MyTable table = new MyTable();

		@RunsInEDT
		static MyWindow createNew() {
			return execute(MyWindow::new);
		}

		private MyWindow() {
			super(JTableHeaderQuery_tableHeader_Test.class);
			addComponents(table);
		}
	}

	private static class MyTable extends TestTable {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		MyTable() {
			super(2, 6);
		}

		@Override
		public JTableHeader getTableHeader() {
			if (recording) {
				methodInvocations.invoked("getTableHeader");
			}
			return super.getTableHeader();
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName) {
			return methodInvocations.requireInvoked(methodName);
		}
	}
}
