package engineTester;
 
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModels;
import renderEngine.DisplayManager;
import renderEngine.Loader;
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
        
         
//        float[] vertices = {            
//                -0.5f,0.5f,0,   //V0
//                -0.5f,-0.5f,0,  //V1
//                0.5f,-0.5f,0,   //V2
//                0.5f,0.5f,0     //V3
//        };
//         
//        int[] indices = {
//                0,1,3,  //Top left triangle (V0,V1,V3)
//                3,1,2   //Bottom right triangle (V3,V1,V2)
//        };
//         
//        float[] textureCoords = {
//        		0, 0,	//V0
//        		0, 1,	//V1
//        		1, 1,	//V2
//        		1, 0	//V3
//        };
        
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		
		float[] textureCoords = {
				
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0

				
		};
		
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
        
        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture modelTexture = new ModelTexture(loader.loadTexture("elephant"));
        TexturedModels texturedModel = new TexturedModels(model, modelTexture);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -5), 0, 0, 0, 1f);
        while(!Display.isCloseRequested()){
        	camera.move();
        	
//        	entity.increasePosition(0, 0, -.002f);
//        	entity.increaseRotation(0, 0, 0);
            //game logic
        	entity.increaseRotation(1, 1, 0);
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();         
        }
 
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}