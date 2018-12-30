package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModels;
import shader.StaticShader;

public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	//contains the texturedmodels as the key and a list of the entities.  Avoid loading attributes, textures, and shine damper over and over.
	private Map<TexturedModels, List<Entity>> entities = new HashMap<>();
	
	/**
	 * render every entity
	 * @param sun
	 * @param camera
	 */
	public void render(Light sun, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	/**
	 * When creating an entity, check to see if the map contains that key
	 * if it doesn't create a new list and add it to the list then put it into the hashmap
	 * if it does exist, get the existing list and add the entity
	 * @param entity
	 */
	public void processEntity(Entity entity) {
		TexturedModels entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	
	public void cleanUp() {
		shader.cleanUp();
	}

}
