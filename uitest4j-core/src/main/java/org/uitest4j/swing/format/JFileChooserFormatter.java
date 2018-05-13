/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.format;

import org.uitest4j.swing.annotation.RunsInCurrentThread;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static javax.swing.JFileChooser.*;

/**
 * Formatter for {@code JFileChooser}s.
 *
 * @author Yvonne Wang
 */
public class JFileChooserFormatter extends ComponentFormatterTemplate {
  private static final IntEnum DIALOG_TYPES = new IntEnum();
  static {
    DIALOG_TYPES.put(OPEN_DIALOG, "OPEN_DIALOG").put(SAVE_DIALOG, "SAVE_DIALOG").put(CUSTOM_DIALOG, "CUSTOM_DIALOG");
  }

  /**
   * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JFileChooser}.
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code JFileChooser}.
   */
  @RunsInCurrentThread
  @Override
  @Nonnull protected String doFormat(@Nonnull Component c) {
    JFileChooser fileChooser = (JFileChooser) c;
	  String format = "%s[name='%s', dialogTitle='%s', dialogType=%s, currentDirectory=%s, enabled=%b, visible=%b, showing=%b";
	  return String.format(format, getRealClassName(c), fileChooser.getName(),
			  fileChooser.getDialogTitle(), DIALOG_TYPES.get(fileChooser.getDialogType()),
              fileChooser.getCurrentDirectory().getAbsolutePath(),
                         fileChooser.isEnabled(),
                         fileChooser.isVisible(), fileChooser.isShowing());
  }

  /**
   * @return {@code JFileChooser.class}.
   */
  @Override
  @Nonnull public Class<? extends Component> targetType() {
    return JFileChooser.class;
  }
}
