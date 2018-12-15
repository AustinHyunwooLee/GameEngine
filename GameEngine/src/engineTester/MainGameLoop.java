package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		//Vertices of the two triangles that create a square CCW
		float[] position = {
				-0.5f, 0.5f, 0,		
				-0.5f, -0.5f, 0,	
				0.5f, 0.5f, 0,		
				0.5f, 0.5f, 0,
				-0.5f, -0.5f, 0,	
				0.5f, -0.5f, 0,		
				
		};
		
		Loader loader = new Loader();
		RawModel model = loader.loadToVao(position);
		Renderer renderer = new Renderer();
		
		while(!Display.isCloseRequested()) {
			DisplayManager.updateDisplay();
			renderer.prepare();
			renderer.render(model);
			
		}
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
