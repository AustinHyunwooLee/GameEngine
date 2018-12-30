package engineTester;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModels;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shader.StaticShader;
import textures.ModelTexture;
 
public class MainGameLoop {
	
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Camera camera = new Camera();
        
         
        
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
        
        
        MasterRenderer renderer = new MasterRenderer();
        
        List<Entity> dragons = new ArrayList<>();
        Random random = new Random();
        dragons.add(new Entity(texturedModel, new Vector3f(0, 0, -25), 0, 0, 0, 1f));
        for(int i = 0; i < 10; i++) {
        	float x = random.nextFloat() * 100 - 50;
        	float y = random.nextFloat() * 100 - 50;
        	float z = random.nextFloat() * -300;
        	dragons.add(new Entity(texturedModel, new Vector3f(x, y, z), 0f, 0f, 0f, 1f));
        }
        System.out.println(dragons.size());
        
        
        while(!Display.isCloseRequested()){
        	camera.move();
//        	entity.increasePosition(0, 0, -.002f);
//        	entity.increaseRotation(0, 0, 0);
            //game logic
//        	entity.increaseRotation(0, 0.9f, 0);
        	for(Entity dragon : dragons) {
        		renderer.processEntity(dragon);
        	}
        	renderer.render(light, camera);
            DisplayManager.updateDisplay();         
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}