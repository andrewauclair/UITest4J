/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2012-2015 the original author or authors.
 */
package org.uitest4j.swing.test.builder;

import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.edt.GuiQuery;

import javax.swing.*;

import static org.uitest4j.swing.edt.GuiActionRunner.execute;

/**
 * Factory of {@code JSlider}s.
 *
 * @author Alex Ruiz
 */
public final class JSliders {
	private JSliders() {
	}

	public static JSliderFactory slider() {
		return new JSliderFactory();
	}

	public static class JSliderFactory {
		int maximum;
		int minimum;
		String name;
		int value;

		public JSliderFactory withMaximum(int newMaximum) {
			maximum = newMaximum;
			return this;
		}

		public JSliderFactory withMinimum(int newMinimum) {
			minimum = newMinimum;
			return this;
		}

		public JSliderFactory withName(String newName) {
			name = newName;
			return this;
		}

		public JSliderFactory withValue(int newValue) {
			value = newValue;
			return this;
		}

		@RunsInEDT
		public JSlider createNew() {
			return execute(new GuiQuery<>() {
				@Override
				protected JSlider executeInEDT() {
					JSlider slider = new JSlider();
					slider.setMaximum(maximum);
					slider.setMinimum(minimum);
					slider.setName(name);
					slider.setValue(value);
					return slider;
				}
			});
		}
	}
}