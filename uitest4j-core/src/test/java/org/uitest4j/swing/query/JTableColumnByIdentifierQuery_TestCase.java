/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.query;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.test.core.MethodInvocations;
import org.uitest4j.swing.test.core.MethodInvocations.Args;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.TestTable;
import org.uitest4j.swing.test.swing.TestWindow;

import javax.swing.table.TableColumn;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.test.core.MethodInvocations.Args.args;

/**
 * Base test case for {@link JTableColumnByIdentifierQuery}.
 *
 * @author Alex Ruiz
 */
public abstract class JTableColumnByIdentifierQuery_TestCase extends RobotBasedTestCase {
	MyTable table;

	@Override
	protected final void onSetUp() {
		MyWindow window = MyWindow.createNew(getClass());
		table = window.table;
	}

	@RunsInEDT
	final int columnIndexByIdentifier(String identifier) {
		return columnIndexByIdentifier(table, identifier);
	}

	@RunsInEDT
	private static int columnIndexByIdentifier(final MyTable table, final String identifier) {
		return execute(() -> JTableColumnByIdentifierQuery.columnIndexByIdentifier(table, identifier));
	}

	static class MyWindow extends TestWindow {
		final MyTable table = new MyTable();

		@RunsInEDT
		static MyWindow createNew(final Class<?> testClass) {
			return execute(() -> new MyWindow(testClass));
		}

		private MyWindow(Class<?> testClass) {
			super(testClass);
			addComponents(table);
		}
	}

	static class MyTable extends TestTable {
		private boolean recording;
		private final MethodInvocations methodInvocations = new MethodInvocations();

		MyTable() {
			super(2, 6);
		}

		@Override
		public TableColumn getColumn(Object identifier) {
			if (recording) {
				methodInvocations.invoked("getColumn", args(identifier));
			}
			return super.getColumn(identifier);
		}

		void startRecording() {
			recording = true;
		}

		MethodInvocations requireInvoked(String methodName, Args args) {
			return methodInvocations.requireInvoked(methodName, args);
		}
	}
}
