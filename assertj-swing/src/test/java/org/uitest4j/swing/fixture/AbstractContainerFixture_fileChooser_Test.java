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

import org.uitest4j.swing.core.GenericTypeMatcher;
import org.uitest4j.swing.exception.ComponentLookupException;
import org.uitest4j.swing.exception.WaitTimedOutError;
import org.uitest4j.swing.test.ExpectedException;
import org.uitest4j.swing.test.core.RobotBasedTestCase;
import org.uitest4j.swing.test.swing.JFileChooserLauncherWindow;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;
import static org.uitest4j.swing.timing.Timeout.timeout;

/**
 * Tests lookups of {@code JFileChooser}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_fileChooser_Test extends RobotBasedTestCase {
  private ContainerFixture fixture;
  private JFileChooserLauncherWindow window;

  @Override
  protected final void onSetUp() {
    window = JFileChooserLauncherWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  void should_Find_Visible_JFileChooser_By_Name() {
    robot.showWindow(window);
    launchFileChooserNow();
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser");
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  void should_Fail_If_Visible_JFileChooser_Not_Found_By_Name() {
    ExpectedException.assertContainsMessage(ComponentLookupException.class, () -> fixture.fileChooser("myFileChooser"), "Unable to find component using matcher",
        "name='myFileChooser', type=javax.swing.JFileChooser, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JFileChooser_By_Name_With_Timeout() {
    robot.showWindow(window);
    launchFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser("fileChooser", timeout(300));
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  void should_Fail_If_Visible_JFileChooser_Not_Found_By_Name_With_Timeout() {
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> fixture.fileChooser("fileChooser", timeout(300)), "Timed out waiting for file chooser to be found");
  }

  @Test
  void should_Find_Visible_JFileChooser_By_Type() {
    robot.showWindow(window);
    launchFileChooserNow();
    JFileChooserFixture fileChooser = fixture.fileChooser();
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  void should_Fail_If_Visible_JFileChooser_Not_Found_By_Type() {
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> fixture.fileChooser(), "Unable to find component using matcher",
        "type=javax.swing.JFileChooser, requireShowing=true");
  }

  @Test
  void should_Find_Visible_JFileChooser_By_Type_With_Timeout() {
    robot.showWindow(window);
    launchFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser(timeout(300));
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  void should_Fail_If_Visible_JFileChooser_Not_Found_By_Type_With_Timeout() {
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> fixture.fileChooser(timeout(100)), "Timed out waiting for file chooser to be found");
  }

  @Test
  void should_Find_Visible_JFileChooser_By_Matcher() {
    robot.showWindow(window);
    launchFileChooserNow();
    JFileChooserFixture fileChooser = fixture.fileChooser(new JFileChooserByTitleMatcher());
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  void should_Fail_If_Visible_JFileChooser_Not_Found_By_Matcher() {
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> fixture.fileChooser(new JFileChooserByTitleMatcher()), "Timed out waiting for file chooser to be found");
  }

  @Test
  void should_Find_Visible_JFileChooser_By_Matcher_With_Timeout() {
    robot.showWindow(window);
    launchFileChooser(200);
    JFileChooserFixture fileChooser = fixture.fileChooser(new JFileChooserByTitleMatcher(), timeout(300));
    assertThat(fileChooser.target()).isSameAs(window.fileChooser());
  }

  @Test
  void should_Fail_If_Visible_JFileChooser_Not_Found_By_Matcher_With_Timeout() {
    ExpectedException.assertContainsMessage(WaitTimedOutError.class, () -> fixture.fileChooser(new JFileChooserByTitleMatcher(), timeout(300)), "Timed out waiting for file chooser to be found");
  }

  private void launchFileChooserNow() {
    launchFileChooser(0);
  }

  private void launchFileChooser(int delay) {
    window.launchDelay(delay);
    fixture.button("browse").click();
  }

  private static class JFileChooserByTitleMatcher extends GenericTypeMatcher<JFileChooser> {
    private static final String TITLE = "Launched JFileChooser";

    private JFileChooserByTitleMatcher() {
      super(JFileChooser.class);
    }

    @Override
    protected boolean isMatching(@Nonnull JFileChooser fileChooser) {
      return TITLE.equals(fileChooser.getDialogTitle());
    }

    @Override
    @Nonnull public String toString() {
      return concat("file chooser with title ", quote(TITLE));
    }
  }
}
