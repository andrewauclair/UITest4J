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
 * Copyright 2012-2015 the original author or authors.
 */

/**
 * Module info for UITest4J Core
 *
 * @author Andrew Auclair
 */
// TODO For now we're exporting everything but the internal package and start moving things into the internal package if they shouldn't be exposed
module org.uitest4j.core {
	requires org.opentest4j;
	requires java.desktop;
	requires java.logging;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.swing;
	requires jsr305;

	exports org.uitest4j.swing.annotation;
	exports org.uitest4j.swing.assertions;
	exports org.uitest4j.swing.awt;
	exports org.uitest4j.swing.cell;

	// TODO This needs to be exported for the Robot class, but there might be other internal classes we would want to hide that exist in org.uitest4j.swing.core
	exports org.uitest4j.swing.core;

	exports org.uitest4j.swing.data;

	// TODO Some of these are internal, but they have to be exported because you can override a fixture and create a driver, maybe move the internal drivers to a org.uitest4j.swing.driver.internal package in the future or org.uitest4j.swing.internal.driver
	exports org.uitest4j.swing.driver;

	exports org.uitest4j.swing.edt;
	exports org.uitest4j.swing.exception;
	exports org.uitest4j.swing.finder;
	exports org.uitest4j.swing.fixture;
	exports org.uitest4j.swing.format;
	exports org.uitest4j.swing.hierarchy;
	exports org.uitest4j.swing.image;
	exports org.uitest4j.swing.input;
	exports org.uitest4j.swing.keystroke;
	exports org.uitest4j.swing.launcher;
	exports org.uitest4j.swing.listener;
	exports org.uitest4j.swing.lock;
	exports org.uitest4j.swing.monitor;
	exports org.uitest4j.swing.query;
	exports org.uitest4j.swing.security;
	exports org.uitest4j.swing.testing;
	exports org.uitest4j.swing.text;
	exports org.uitest4j.swing.timing;
	exports org.uitest4j.swing.util;
}
