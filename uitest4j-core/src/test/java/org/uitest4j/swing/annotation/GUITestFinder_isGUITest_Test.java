/**
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
package org.uitest4j.swing.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GUITestFinder#isGUITest(Class, Method)}.
 *
 * @author Alex Ruiz
 */
class GUITestFinder_isGUITest_Test {
	@GUITest
	public static class GUITestClass {
		@GUITest
		public void guiTestMethodWithAnnotation() {
		}

		public void guiTestMethodWithoutAnnotation() {
		}
	}

	public static class NonGUITestClass {
		@GUITest
		public void guiTestMethod() {
		}
	}

	public static class GUITestSubclass extends GUITestClass {
		@Override
		public void guiTestMethodWithoutAnnotation() {
			super.guiTestMethodWithoutAnnotation();
		}
	}

	public static class NonGUITestSubclass extends NonGUITestClass {
		@Override
		public void guiTestMethod() {
			super.guiTestMethod();
		}
	}

	private GUITestClass guiTest;
	private NonGUITestClass nonGUITest;
	private GUITestSubclass guiTestSubclass;
	private NonGUITestSubclass nonGUITestSubclass;

	@BeforeEach
	void setUp() {
		guiTest = new GUITestClass();
		nonGUITest = new NonGUITestClass();
		guiTestSubclass = new GUITestSubclass();
		nonGUITestSubclass = new NonGUITestSubclass();
	}

	@Test
	void should_Return_True_If_Class_Has_GUITest_Annotation() throws NoSuchMethodException {
		Class<? extends GUITestClass> guiTestType = guiTest.getClass();
		boolean isGUITest = GUITestFinder.isGUITest(guiTestType, guiTest.getClass().getMethod("guiTestMethodWithoutAnnotation"));
		assertThat(isGUITest).isTrue();
	}

	@Test
	void should_Return_True_If_Only_One_Method_Has_GUITest_Annotation() throws NoSuchMethodException {
		Class<? extends NonGUITestClass> nonGUITestType = nonGUITest.getClass();
		boolean isGUITest = GUITestFinder.isGUITest(nonGUITestType, nonGUITest.getClass().getMethod("guiTestMethod"));
		assertThat(isGUITest).isTrue();
	}

	@Test
	void should_Return_True_If_Superclass_Is_GUI_Test() throws NoSuchMethodException {
		Class<? extends GUITestSubclass> guiTestSubtype = guiTestSubclass.getClass();
		boolean isGUITest = GUITestFinder.isGUITest(guiTestSubtype, guiTestSubclass.getClass().getMethod("guiTestMethodWithoutAnnotation"));
		assertThat(isGUITest).isTrue();
	}

	@Test
	void should_Return_True_If_Overriden_Method_Is_GUI_Test() throws NoSuchMethodException {
		Class<? extends NonGUITestSubclass> nonGUITestSubtype = nonGUITestSubclass.getClass();
		boolean isGUITest = GUITestFinder.isGUITest(nonGUITestSubtype, nonGUITestSubclass.getClass().getMethod("guiTestMethod"));
		assertThat(isGUITest).isTrue();
	}

	@Test
	void should_Return_False_If_Not_Containing_GUITest_Annotation() throws NoSuchMethodException {
		String s = "Yoda";
		Method concat = String.class.getMethod("concat", String.class);
		assertThat(GUITestFinder.isGUITest(s.getClass(), concat)).isFalse();
	}
}
