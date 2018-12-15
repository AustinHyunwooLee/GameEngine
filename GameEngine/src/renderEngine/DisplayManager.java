package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	//Resolution and FPS
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS = 120;
	
	/**
	 * Creates a display.  Use the whole display to show the game.
	 */
	public static void createDisplay() {
		 ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		 try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Display");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		 GL11.glViewport(0, 0, WIDTH, HEIGHT);
		 
	}
	
	/**
	 * Updates the display every frame
	 */
	public static void updateDisplay() {
		Display.sync(FPS);
		Display.update();
	}
	
	/**
	 * Closes the display
	 */
	public static void closeDisplay() {
		Display.destroy();
	}

}
