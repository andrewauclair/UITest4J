/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.test.recorder;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;

public class ClickRecorderManager {//implements TestRule {
  private List<ToolkitClickRecorder> attachedToolkitClickRecorders = newArrayList();

//  @Override
//  public Statement apply(Statement base, Description description) {
//    return new RecorderStatement(base);
//  }

  @Nonnull public ClickRecorder attachDirectlyTo(@Nonnull Component target) {
    return ClickRecorder.attachTo(target);
  }

  @Nonnull public ToolkitClickRecorder attachToToolkitFor(@Nonnull Component target) {
    ToolkitClickRecorder clickRecorder = ToolkitClickRecorder.attachTo(target);
    attachedToolkitClickRecorders.add(clickRecorder);
    return clickRecorder;
  }

//  private class RecorderStatement extends Statement {
//
//    private Statement base;
//
//    public RecorderStatement(Statement base) {
//      this.base = base;
//    }
//
//    @Override
//    public void evaluate() throws Throwable {
//      try {
//        base.evaluate();
//      } finally {
//        for (ToolkitClickRecorder recorder : attachedToolkitClickRecorders) {
//          ToolkitClickRecorder.remove(recorder);
//        }
//        attachedToolkitClickRecorders.clear();
//      }
//    }
//  }
}
