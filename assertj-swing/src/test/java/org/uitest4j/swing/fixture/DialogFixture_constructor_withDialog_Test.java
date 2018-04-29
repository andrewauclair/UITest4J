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
package org.uitest4j.swing.fixture;

import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.test.ScreenLockReleaser;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JDialogs.dialog;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link DialogFixture#DialogFixture(Dialog)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class DialogFixture_constructor_withDialog_Test extends EDTSafeTestCase {
  private DialogFixture fixture;

  @AfterEach
  void tearDown() {
    if (fixture != null) {
      fixture.cleanUp();
    }
    ScreenLock screenLock = ScreenLock.instance();
    if (screenLock.acquired()) {
      screenLock.release(screenLock.getOwner());
    }
  }

  @Test
  void should_Create_New_Robot_And_Use_Given_Dialog_As_Target() {
    Dialog target = dialog().createNew();
    fixture = new DialogFixture(target);
    assertThat(fixture.robot()).isNotNull();
    assertThat(fixture.target()).isSameAs(target);
  }

  @Test
  void should_Throw_Error_If_Dialog_Is_Null() {
    // jsr305 throws IllegalArgumentExceptions when @Nonnull is used
    assertThrows(IllegalArgumentException.class, () -> fixture = new DialogFixture((Dialog) null));
  }
}
