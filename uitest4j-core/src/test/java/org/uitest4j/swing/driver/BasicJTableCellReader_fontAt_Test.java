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

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Tests for {@link BasicJTableCellReader#fontAt(JTable, int, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJTableCellReader_fontAt_Test extends BasicJTableCellReader_TestCase {
	@Test
	public void should_Return_Font_From_CellRenderer() {
		JLabel label = setJLabelAsCellRenderer();
		Font font = fontAt(reader, table, 0, 0);
		assertThat(font).isEqualTo(fontOf(label));
	}

	@RunsInEDT
	private static Font fontAt(final BasicJTableCellReader reader, final JTable table, final int row, final int column) {
		return execute(() -> reader.fontAt(table, row, column));
	}

	@RunsInEDT
	@Nonnull
	private static Font fontOf(final @Nonnull Component component) {
		Font result = execute(component::getFont);
		return checkNotNull(result);
	}
}
