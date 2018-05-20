/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2018 the original author or authors.
 */
package org.uitest4j.javafx.test;

import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.awt.geom.Point2D;

/**
 * @author Andrew Auclair
 */
public class TestStage extends Stage {
	static final Point2D DEFAULT_WINDOW_LOCATION = new Point2D.Double(100.0, 100.0);

	protected TestStage(@Nonnull Class<?> testClass) {
		setTitle(testClass.getSimpleName());
	}
}
