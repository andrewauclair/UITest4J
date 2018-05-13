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
package org.uitest4j.swing.launcher;

import org.uitest4j.swing.exception.UnexpectedException;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import static org.uitest4j.swing.util.ArrayUtils.copyOf;

/**
 * <p>
 * Executes a Java application from a class that has a "main" method.
 * </p>
 *
 * <p>
 * The following example shows how to start an application without any arguments:
 * </p>
 *
 * <pre>
 * ApplicationLauncher.application(JavaApp.class).start();
 *
 * // or
 *
 * ApplicationLauncher.{@link #application(String) application}(&quot;org.assertj.swing.application.JavaApp&quot;).{@link #start() start}();
 * </pre>
 *
 * <p>
 * The following example shows how to start an application with arguments:
 * </p>
 *
 * <pre>
 * ApplicationLauncher.{@link #application(Class) application}(JavaApp.class).{@link #withArgs(String...) withArgs}(&quot;arg1&quot;, &quot;arg2&quot;).{@link #start() start}();
 *
 * // or
 *
 * ApplicationLauncher.{@link #application(String) application}(&quot;org.assertj.swing.application.JavaApp&quot;).{@link #withArgs(String...) withArgs}(&quot;arg1&quot;, &quot;arg2&quot;).{@link #start() start}();
 * </pre>
 *
 * @author Yvonne Wang
 */
public class ApplicationLauncher {
	/**
	 * Starting point of the fluent interface.
	 *
	 * @param applicationTypeName the fully qualified name of the class containing the "main" method.
	 * @return the created {@code ApplicationStarter}.
	 * @throws UnexpectedException if the class specified in the given name cannot be loaded.
	 */
	@Nonnull
	public static ApplicationLauncher application(@Nonnull String applicationTypeName) {
		try {
			Class<?> applicationType = Thread.currentThread().getContextClassLoader().loadClass(applicationTypeName);
			return application(applicationType);
		}
		catch (ClassNotFoundException e) {
			throw new UnexpectedException(String.format("Unable to load class '%s'", applicationTypeName), e);
		}
	}

	/**
	 * Starting point of the fluent interface.
	 *
	 * @param applicationType the class containing the "main" method.
	 * @return the created {@code ApplicationStarter}.
	 */
	@Nonnull
	public static ApplicationLauncher application(@Nonnull Class<?> applicationType) {
		return new ApplicationLauncher(applicationType);
	}

	private final Class<?> applicationType;
	private String[] args = {};

	private ApplicationLauncher(@Nonnull Class<?> applicationType) {
		this.applicationType = applicationType;
	}

	/**
	 * Specifies the arguments to pass to the "main" method. Please note that the arguments to pass are specific to your
	 * application. JVM-specific arguments are ignored (e.g. -Xms, -Xmx)
	 *
	 * @param newArgs the arguments to pass to the "main" method.
	 * @return this {@code ApplicationStarter}.
	 * @throws NullPointerException if {@code newArgs} is {@code null}.
	 */
	@Nonnull
	public ApplicationLauncher withArgs(@Nonnull String... newArgs) {
		args = copyOf(Objects.requireNonNull(newArgs));
		return this;
	}

	/**
	 * Starts the application.
	 *
	 * @throws NoSuchMethodException If the "main" method cannot be found.
	 * @throws InvocationTargetException If there was an exception invoking the "main" method.
	 * @throws IllegalAccessException If the "main" method is not accessible.
	 */
	public void start() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method main = applicationType.getMethod("main", String[].class);
		main.invoke(applicationType, new Object[]{args});
//		method("main").withParameterTypes(String[].class).in(applicationType).invoke(new Object[]{args});
	}
}
