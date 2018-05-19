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
package org.uitest4j.core.api;

import javax.annotation.Nullable;

/**
 * @author Alex Ruiz
 * @author Andrew Auclair
 */
public interface Matcher<C> {
	/**
	 * Indicates whether the given AWT or Swing {@code Component} matches some lookup criteria.
	 *
	 * @param c the {@code Component} to verify.
	 * @return {@code true} if the given {@code Component} matches some lookup criteria, otherwise {@code false}.
	 */
	boolean matches(@Nullable C c);
}
