package engineTester;
 
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModels;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shader.StaticShader;
import textures.ModelTexture;
 
public class MainGameLoop {
	
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        
        StaticShader shader = new StaticShader();
        Camera camera = new Camera();
        Renderer renderer = new Renderer(shader);
        
         
        
        //Update
//        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
//        RawModel model = OBJLoader.loadObjModel("stall", loader);
//        ModelTexture modelTexture = new ModelTexture(loader.loadTexture("stallTexture"));
        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModels texturedModel = new TexturedModels(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture staticTexture = texturedModel.getTexture();
		staticTexture.setReflectivity(1);
		staticTexture.setShineDamper(1);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25), 0, 0, 0, 1f);
        //Currently can only spawn in one light source
        Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        
        while(!Display.isCloseRequested()){
        	camera.move();
//        	entity.increasePosition(0, 0, -.002f);
//        	entity.increaseRotation(0, 0, 0);
            //game logic
        	entity.increaseRotation(0, 0.9f, 0);
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            shader.loadLight(light);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();         
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}