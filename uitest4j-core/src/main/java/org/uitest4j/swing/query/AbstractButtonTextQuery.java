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
package org.uitest4j.swing.query;

import org.uitest4j.swing.annotation.RunsInEDT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Returns the text of a Swing {@code AbstractButton}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @see AbstractButton#getText()
 */
public final class AbstractButtonTextQuery {
	/**
	 * Returns the text of the given Swing {@code AbstractButton}.
	 *
	 * @param button the given {@code AbstractButton}.
	 * @return the text of the given Swing {@code AbstractButton}.
	 */
	@RunsInEDT
	@Nullable
	public static String textOf(final @Nonnull AbstractButton button) {
		return execute(button::getText);
	}

	private AbstractButtonTextQuery() {
	}
}