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
package org.uitest4j.swing.awt;

import org.uitest4j.swing.annotation.RunsInCurrentThread;
import org.uitest4j.swing.annotation.RunsInEDT;
import org.uitest4j.swing.util.ToolkitProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
import static org.uitest4j.swing.edt.GuiActionRunner.execute;
import static org.uitest4j.swing.util.Platform.isWindows;

/**
 * Utility methods related to AWT.
 *
 * @author Alex Ruiz
 */
public class AWT {
	private static final String ROOT_FRAME_CLASSNAME = SwingUtilities.class.getName() + "$";
	private static final ToolkitProvider TOOLKIT_PROVIDER = ToolkitProvider.instance();

	/**
	 * Indicates whether the given point, relative to the given {@code JComponent}, is inside the screen boundaries.
	 *
	 * @param c the given {@code JComponent}.
	 * @param p the point to verify.
	 * @return {@code true} if the point is inside the screen boundaries; {@code false} otherwise.
	 */
	@RunsInCurrentThread
	public static boolean isPointInScreenBoundaries(@Nonnull JComponent c, @Nonnull Point p) {
		Point where = translate(c, p.x, p.y);
		return isPointInScreenBoundaries(where);
	}

	/**
	 * Indicates whether the given point is inside the screen boundaries.
	 *
	 * @param p the point to verify.
	 * @return {@code true} if the point is inside the screen boundaries; {@code false} otherwise.
	 */
	public static boolean isPointInScreenBoundaries(@Nonnull Point p) {
		for (GraphicsDevice screen : getLocalGraphicsEnvironment().getScreenDevices()) {
			for (GraphicsConfiguration conf : screen.getConfigurations()) {
				if (conf.getBounds().contains(p)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Translates the given coordinates to the location on screen of the given AWT or Swing {@code Component}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given {@code Component}.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return the translated coordinates.
	 */
	@RunsInCurrentThread
	@Nonnull
	public static Point translate(@Nonnull Component c, int x, int y) {
		Point p = locationOnScreenOf(c);
		p.translate(x, y);
		return p;
	}

	/**
	 * Returns a point at the center of the visible area of the given AWT or Swing {@code Component}.
	 *
	 * @param c the given {@code Component}.
	 * @return a point at the center of the visible area of the given {@code Component}.
	 */
	@RunsInEDT
	@Nonnull
	public static Point visibleCenterOf(@Nonnull final Component c) {
		Point center = execute(() -> {
			if (c instanceof JComponent) {
				return centerOfVisibleRect((JComponent) c);
			}
			return centerOf(c);
		});
		return Objects.requireNonNull(center);
	}

	/**
	 * <p>
	 * Returns a point at the center of the given AWT or Swing {@code Component}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given {@code Component}.
	 * @return a point at the center of the given {@code Component}.
	 */
	@RunsInCurrentThread
	@Nonnull
	public static Point centerOf(@Nonnull Component c) {
		Dimension size = c.getSize();
		return new Point(size.width / 2, size.height / 2);
	}

	/**
	 * <p>
	 * Returns a point at the center of the visible rectangle of the given {@code JComponent}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given {@code JComponent}.
	 * @return a point at the center of the visible rectangle of the given {@code JComponent}.
	 */
	@RunsInCurrentThread
	@Nonnull
	public static Point centerOfVisibleRect(@Nonnull JComponent c) {
		Rectangle r = c.getVisibleRect();
		return centerOf(Objects.requireNonNull(r));
	}

	/**
	 * <p>
	 * Returns a point at the center of the given {@code Rectangle}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param r the given {@code Rectangle}.
	 * @return a point at the center of the given {@code Rectangle}.
	 */
	@RunsInCurrentThread
	@Nonnull
	public static Point centerOf(@Nonnull Rectangle r) {
		return new Point((r.x + (r.width / 2)), (r.y + (r.height / 2)));
	}

	/**
	 * <p>
	 * Returns the insets of the given AWT or Swing {@code Container}, or an empty one if no insets can be found.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given {@code Container}.
	 * @return the insets of the given {@code Container}, or an empty one if no insets can be found.
	 */
	@RunsInCurrentThread
	@Nonnull
	public static Insets insetsFrom(@Nonnull Container c) {
		try {
			Insets insets = c.getInsets();
			if (insets != null) {
				return insets;
			}
		}
		catch (Exception e) {
		}
		return new Insets(0, 0, 0, 0);
	}

	/**
	 * Returns {@code true} if the given component is an Applet viewer.
	 *
	 * @param c the component to check.
	 * @return {@code true} if the given component is an Applet viewer, {@code false} otherwise.
	 */
	public static boolean isAppletViewer(@Nullable Component c) {
		return c != null && "sun.applet.AppletViewer".equals(c.getClass().getName());
	}

	/**
	 * Returns whether the given component is the default Swing hidden frame.
	 *
	 * @param c the component to check.
	 * @return {@code true} if the given component is the default hidden frame, {@code false} otherwise.
	 */
	public static boolean isSharedInvisibleFrame(@Nullable Component c) {
		if (c == null) {
			return false;
		}
		// Must perform an additional check, since applets may have their own version in their AppContext
		return c instanceof Frame
				&& (c == JOptionPane.getRootFrame() || c.getClass().getName().startsWith(ROOT_FRAME_CLASSNAME));
	}

	/**
	 * <p>
	 * Returns whether the given AWT or Swing {@code Component} is a heavy-weight pop-up, that is, a container for a
	 * {@code JPopupMenu} that is implemented with a heavy-weight component (usually an AWT or Swing {@code Window}).
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given {@code Component}.
	 * @return {@code true} if the given {@code Component} is a heavy-weight pop-up; {@code false} otherwise.
	 */
	@RunsInCurrentThread
	public static boolean isHeavyWeightPopup(@Nonnull Component c) {
		if (!(c instanceof Window) || c instanceof Dialog || c instanceof Frame) {
			return false;
		}
		String name = obtainNameSafely(c);
		if ("###overrideRedirect###".equals(name) || "###focusableSwingPopup###".equals(name)) {
			return true;
		}
		String typeName = c.getClass().getName();
		return typeName.contains("PopupFactory$WindowPopup") || typeName.contains("HeavyWeightWindow");
	}

	@RunsInCurrentThread
	@Nullable
	private static String obtainNameSafely(@Nonnull Component c) {
		// Work around some components throwing exceptions if getName is called prematurely
		try {
			return c.getName();
		}
		catch (Throwable e) {
			return null;
		}
	}

	/**
	 * <p>
	 * Returns the invoker, if any, of the given AWT or Swing {@code Component}; or {@code null}, if the {@code Component}
	 * is not on a pop-up of any sort.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given {@code Component}.
	 * @return the invoker, if any, of the given {@code Component}; or {@code null}, if the {@code Component} is not on a
	 * pop-up of any sort.
	 */
	@RunsInCurrentThread
	@Nullable
	public static Component invokerOf(final @Nonnull Component c) {
		if (c instanceof JPopupMenu) {
			return ((JPopupMenu) c).getInvoker();
		}
		Container parent = c.getParent();
		return parent != null ? invokerOf(parent) : null;
	}

	/**
	 * <p>
	 * Wrapper for {@code Component.getLocationOnScreen}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
	 * dispatch thread (EDT). Client code must call this method from the EDT.
	 * </p>
	 *
	 * @param c the given AWT or Swing {@code Component}.
	 * @return the a point specifying the {@code Component}'s top-left corner in the screen's coordinate space.
	 */
	@RunsInCurrentThread
	@Nonnull
	public static Point locationOnScreenOf(@Nonnull Component c) {
		return new Point(c.getLocationOnScreen());
	}

	/**
	 * Returns whether the platform registers a pop-up on mouse press.
	 *
	 * @return {@code true} if the platform registers a pop-up on mouse press, {@code false} otherwise.
	 */
	public static boolean popupOnPress() {
		// Only w32 is pop-up on release
		return !isWindows();
	}

	/**
	 * @return the {@code InputEvent} mask for the pop-up trigger button.
	 */
	public static int popupMask() {
		return BUTTON3_DOWN_MASK;
	}

	private AWT() {
	}
}
