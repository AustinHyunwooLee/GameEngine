package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		float[] position = {
				-0.5f, 0.5f, 0,	//V1
				0.5f, 0.5f, 0,	//V2
				0.5f, -0.5f, 0,	//V3
				-0.5f, -0.5f, 0	//V4
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
